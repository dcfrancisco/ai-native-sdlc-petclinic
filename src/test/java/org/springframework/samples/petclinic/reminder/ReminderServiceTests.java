/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.Visit;

class ReminderServiceTests {

	private final OwnerRepository owners = mock(OwnerRepository.class);

	private final ReminderRepository reminders = mock(ReminderRepository.class);

	private final NotificationGateway notifications = mock(NotificationGateway.class);

	private final ReminderMetrics metrics = mock(ReminderMetrics.class);

	private final ReminderProperties properties = new ReminderProperties();

	private final Clock clock = Clock.fixed(Instant.parse("2026-07-18T08:00:00Z"), ZoneOffset.UTC);

	private ReminderService service;

	@BeforeEach
	void setUp() {
		this.service = new ReminderService(this.owners, this.reminders, this.notifications, this.properties,
				this.metrics, this.clock);
		given(this.notifications.send(any(ReminderMessage.class))).willReturn(NotificationOutcome.ACCEPTED);
	}

	@Test
	void createsOneReminderAcceptedByTheLocalAdapterForAnEligibleVisit() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 20))));
		given(this.reminders.findByVisitId(100)).willReturn(Optional.empty());

		ReminderRun run = this.service.processDueReminders();

		assertThat(run).isEqualTo(new ReminderRun(1, 1, 1, 0, 0, 0));
		ArgumentCaptor<ReminderRecord> saved = ArgumentCaptor.forClass(ReminderRecord.class);
		verify(this.reminders).save(saved.capture());
		assertThat(saved.getValue().getStatus()).isEqualTo(ReminderStatus.ACCEPTED);
		assertThat(saved.getValue().getDestinationHint()).isEqualTo("******1023");
		verify(this.notifications).send(any(ReminderMessage.class));
	}

	@Test
	void doesNotCreateADuplicateForAnExistingVisit() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 20))));
		given(this.reminders.findByVisitId(100)).willReturn(Optional.of(existingReminder()));

		ReminderRun run = this.service.processDueReminders();

		assertThat(run).isEqualTo(new ReminderRun(1, 0, 0, 0, 0, 1));
	}

	@Test
	void recordsASanitizedFailureForRecovery() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 20))));
		given(this.reminders.findByVisitId(100)).willReturn(Optional.empty());
		given(this.reminders.save(any(ReminderRecord.class))).willAnswer(invocation -> invocation.getArgument(0));
		org.mockito.Mockito.doThrow(new IllegalStateException("token=secret-value; destination=6085551023"))
			.when(this.notifications)
			.send(any(ReminderMessage.class));

		ReminderRun run = this.service.processDueReminders();

		assertThat(run).isEqualTo(new ReminderRun(1, 1, 0, 1, 0, 0));
		ArgumentCaptor<ReminderRecord> saved = ArgumentCaptor.forClass(ReminderRecord.class);
		verify(this.reminders).save(saved.capture());
		assertThat(saved.getValue().getStatus()).isEqualTo(ReminderStatus.FAILED);
		assertThat(saved.getValue().getLastError()).isEqualTo("IllegalStateException");
	}

	@Test
	void preservesAnUnknownAdapterOutcomeWithoutClaimingFailureOrAcceptance() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 20))));
		given(this.reminders.findByVisitId(100)).willReturn(Optional.empty());
		given(this.reminders.save(any(ReminderRecord.class))).willAnswer(invocation -> invocation.getArgument(0));
		given(this.notifications.send(any(ReminderMessage.class))).willReturn(NotificationOutcome.UNKNOWN);

		ReminderRun run = this.service.processDueReminders();

		assertThat(run).isEqualTo(new ReminderRun(1, 1, 0, 0, 1, 0));
		ArgumentCaptor<ReminderRecord> saved = ArgumentCaptor.forClass(ReminderRecord.class);
		verify(this.reminders).save(saved.capture());
		assertThat(saved.getValue().getStatus()).isEqualTo(ReminderStatus.UNKNOWN);
		assertThat(saved.getValue().getLastError()).isEqualTo("OutcomeUnknown");
	}

	@Test
	void treatsAMissingAdapterOutcomeAsAFailureRatherThanAcceptance() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 20))));
		given(this.reminders.findByVisitId(100)).willReturn(Optional.empty());
		given(this.reminders.save(any(ReminderRecord.class))).willAnswer(invocation -> invocation.getArgument(0));
		given(this.notifications.send(any(ReminderMessage.class))).willReturn(null);

		ReminderRun run = this.service.processDueReminders();

		assertThat(run).isEqualTo(new ReminderRun(1, 1, 0, 1, 0, 0));
		ArgumentCaptor<ReminderRecord> saved = ArgumentCaptor.forClass(ReminderRecord.class);
		verify(this.reminders).save(saved.capture());
		assertThat(saved.getValue().getStatus()).isEqualTo(ReminderStatus.FAILED);
		assertThat(saved.getValue().getLastError()).isEqualTo("NullPointerException");
	}

	@Test
	void ignoresVisitsOutsideTheConfiguredWindow() {
		given(this.owners.findAll()).willReturn(List.of(ownerWithVisit(LocalDate.of(2026, 7, 21))));

		assertThat(this.service.processDueReminders()).isEqualTo(new ReminderRun(0, 0, 0, 0, 0, 0));
	}

	private Owner ownerWithVisit(LocalDate date) {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setTelephone("6085551023");
		Pet pet = new Pet();
		pet.setName("Leo");
		owner.addPet(pet);
		pet.setId(1);
		Visit visit = new Visit();
		visit.setId(100);
		visit.setDate(date);
		visit.setDescription("annual checkup");
		pet.addVisit(visit);
		return owner;
	}

	private ReminderRecord existingReminder() {
		return new ReminderRecord(100, 1, "Leo", LocalDate.of(2026, 7, 20), "******1023",
				Instant.parse("2026-07-18T08:00:00Z"));
	}

}
