/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderService {

	private final OwnerRepository owners;

	private final ReminderRepository reminders;

	private final NotificationGateway notifications;

	private final ReminderProperties properties;

	private final ReminderMetrics metrics;

	private final Clock clock;

	@Autowired
	public ReminderService(OwnerRepository owners, ReminderRepository reminders, NotificationGateway notifications,
			ReminderProperties properties, ReminderMetrics metrics) {
		this(owners, reminders, notifications, properties, metrics, Clock.systemDefaultZone());
	}

	ReminderService(OwnerRepository owners, ReminderRepository reminders, NotificationGateway notifications,
			ReminderProperties properties, ReminderMetrics metrics, Clock clock) {
		this.owners = owners;
		this.reminders = reminders;
		this.notifications = notifications;
		this.properties = properties;
		this.metrics = metrics;
		this.clock = clock;
	}

	@Transactional
	public ReminderRun processDueReminders() {
		if (!this.properties.isEnabled()) {
			return new ReminderRun(0, 0, 0, 0, 0, 0);
		}
		LocalDate dueDate = LocalDate.now(this.clock).plusDays(this.properties.getLeadDays());
		int eligible = 0;
		int created = 0;
		int accepted = 0;
		int failed = 0;
		int unknown = 0;
		int existing = 0;

		for (Owner owner : this.owners.findAll()) {
			for (Pet pet : owner.getPets()) {
				for (Visit visit : pet.getVisits()) {
					if (!dueDate.equals(visit.getDate())) {
						continue;
					}
					eligible++;
					if (this.reminders.findByVisitId(visit.getId()).isPresent()) {
						existing++;
						continue;
					}
					ReminderRecord reminder = new ReminderRecord(visit.getId(), owner.getId(), pet.getName(),
							visit.getDate(), mask(owner.getTelephone()), Instant.now(this.clock));
					this.reminders.save(reminder);
					this.metrics.created();
					created++;
					ReminderStatus outcome = deliver(reminder, owner.getTelephone());
					switch (outcome) {
						case ACCEPTED -> accepted++;
						case FAILED -> failed++;
						case UNKNOWN -> unknown++;
						default -> throw new IllegalStateException("Unexpected attempt outcome: " + outcome);
					}
				}
			}
		}
		return new ReminderRun(eligible, created, accepted, failed, unknown, existing);
	}

	@Transactional
	public ReminderStatus retry(Integer id) {
		ReminderRecord reminder = this.reminders.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Reminder not found: " + id));
		if (reminder.getStatus() != ReminderStatus.FAILED) {
			return reminder.getStatus();
		}
		Owner owner = this.owners.findById(reminder.getOwnerId())
			.orElseThrow(() -> new IllegalStateException("Owner no longer exists: " + reminder.getOwnerId()));
		return deliver(reminder, owner.getTelephone());
	}

	@Transactional(readOnly = true)
	public List<ReminderRecord> findAll() {
		return this.reminders.findAllByOrderByCreatedAtDesc();
	}

	private ReminderStatus deliver(ReminderRecord reminder, String telephone) {
		Instant attemptTime = Instant.now(this.clock);
		try {
			NotificationOutcome outcome = this.notifications.send(new ReminderMessage(reminder.getOwnerId(), telephone,
					reminder.getPetName(), reminder.getVisitDate()));
			return switch (outcome) {
				case ACCEPTED -> {
					reminder.accepted(attemptTime);
					this.metrics.accepted();
					yield ReminderStatus.ACCEPTED;
				}
				case UNKNOWN -> {
					reminder.unknown(attemptTime);
					this.metrics.unknown();
					yield ReminderStatus.UNKNOWN;
				}
			};
		}
		catch (RuntimeException ex) {
			reminder.failed(attemptTime, safeError(ex));
			this.metrics.failed();
			return ReminderStatus.FAILED;
		}
	}

	private String mask(String destination) {
		if (destination == null || destination.length() < 4) {
			return "hidden";
		}
		return "******" + destination.substring(destination.length() - 4);
	}

	private String safeError(RuntimeException ex) {
		// Provider messages can contain destinations, tokens, or payload fragments.
		// Persist the failure type and keep detailed diagnostics in secured telemetry.
		return ex.getClass().getSimpleName();
	}

}
