/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
class ReminderMetrics {

	private final Counter created;

	private final Counter accepted;

	private final Counter failed;

	private final Counter unknown;

	ReminderMetrics(MeterRegistry registry) {
		this.created = registry.counter("petclinic.reminders.created");
		this.accepted = registry.counter("petclinic.reminders.accepted");
		this.failed = registry.counter("petclinic.reminders.failed");
		this.unknown = registry.counter("petclinic.reminders.unknown");
	}

	void created() {
		this.created.increment();
	}

	void accepted() {
		this.accepted.increment();
	}

	void failed() {
		this.failed.increment();
	}

	void unknown() {
		this.unknown.increment();
	}

}
