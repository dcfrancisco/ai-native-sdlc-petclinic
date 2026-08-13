/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
class ReminderScheduler {

	private final ReminderService reminders;

	ReminderScheduler(ReminderService reminders) {
		this.reminders = reminders;
	}

	@Scheduled(initialDelayString = "${petclinic.reminders.initial-delay-ms:30000}",
			fixedDelayString = "${petclinic.reminders.poll-interval-ms:60000}")
	void process() {
		this.reminders.processDueReminders();
	}

}
