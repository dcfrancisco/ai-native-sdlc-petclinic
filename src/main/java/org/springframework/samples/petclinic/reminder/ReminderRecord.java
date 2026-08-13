/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "reminders", uniqueConstraints = @UniqueConstraint(name = "uk_reminders_visit", columnNames = "visit_id"))
public class ReminderRecord extends BaseEntity {

	@Column(name = "visit_id", nullable = false)
	private Integer visitId;

	@Column(name = "owner_id", nullable = false)
	private Integer ownerId;

	@Column(name = "pet_name", nullable = false, length = 30)
	private String petName;

	@Column(name = "visit_date", nullable = false)
	private LocalDate visitDate;

	@Column(name = "destination_hint", nullable = false, length = 20)
	private String destinationHint;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 16)
	private ReminderStatus status;

	@Column(name = "attempt_count", nullable = false)
	private int attemptCount;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	@Column(name = "last_attempt_at")
	private Instant lastAttemptAt;

	@Column(name = "accepted_at")
	private Instant acceptedAt;

	@Column(name = "last_error", length = 160)
	private String lastError;

	protected ReminderRecord() {
	}

	public ReminderRecord(Integer visitId, Integer ownerId, String petName, LocalDate visitDate, String destinationHint,
			Instant createdAt) {
		this.visitId = visitId;
		this.ownerId = ownerId;
		this.petName = petName;
		this.visitDate = visitDate;
		this.destinationHint = destinationHint;
		this.status = ReminderStatus.PENDING;
		this.createdAt = createdAt;
	}

	public void accepted(Instant at) {
		this.attemptCount++;
		this.lastAttemptAt = at;
		this.acceptedAt = at;
		this.status = ReminderStatus.ACCEPTED;
		this.lastError = null;
	}

	public void failed(Instant at, String error) {
		this.attemptCount++;
		this.lastAttemptAt = at;
		this.status = ReminderStatus.FAILED;
		this.lastError = error;
	}

	public void unknown(Instant at) {
		this.attemptCount++;
		this.lastAttemptAt = at;
		this.status = ReminderStatus.UNKNOWN;
		this.lastError = "OutcomeUnknown";
	}

	public Integer getVisitId() {
		return this.visitId;
	}

	public Integer getOwnerId() {
		return this.ownerId;
	}

	public String getPetName() {
		return this.petName;
	}

	public LocalDate getVisitDate() {
		return this.visitDate;
	}

	public String getDestinationHint() {
		return this.destinationHint;
	}

	public ReminderStatus getStatus() {
		return this.status;
	}

	public int getAttemptCount() {
		return this.attemptCount;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

	public Instant getLastAttemptAt() {
		return this.lastAttemptAt;
	}

	public Instant getAcceptedAt() {
		return this.acceptedAt;
	}

	public String getLastError() {
		return this.lastError;
	}

}
