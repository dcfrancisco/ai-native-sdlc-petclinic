/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Visit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = "petclinic.reminders.initial-delay-ms=3600000")
@Transactional
class ReminderIntegrationTests {

	@Autowired
	private OwnerRepository owners;

	@Autowired
	private ReminderRepository reminders;

	@Autowired
	private ReminderService service;

	@Test
	void persistsOneReminderAndRemainsIdempotent() {
		Owner owner = this.owners.findById(1).orElseThrow();
		Visit visit = new Visit();
		visit.setDate(LocalDate.now().plusDays(2));
		visit.setDescription("companion reminder verification");
		owner.addVisit(1, visit);
		this.owners.saveAndFlush(owner);

		ReminderRun first = this.service.processDueReminders();
		ReminderRun second = this.service.processDueReminders();

		assertThat(first.created()).isEqualTo(1);
		assertThat(first.accepted()).isEqualTo(1);
		assertThat(second.existing()).isEqualTo(1);
		assertThat(this.reminders.findAll()).singleElement()
			.extracting(ReminderRecord::getStatus)
			.isEqualTo(ReminderStatus.ACCEPTED);
	}

}
