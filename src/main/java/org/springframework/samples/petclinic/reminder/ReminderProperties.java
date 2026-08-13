/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("petclinic.reminders")
public class ReminderProperties {

	private boolean enabled = true;

	private int leadDays = 2;

	private long pollIntervalMs = 60000;

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getLeadDays() {
		return this.leadDays;
	}

	public void setLeadDays(int leadDays) {
		if (leadDays < 1 || leadDays > 30) {
			throw new IllegalArgumentException("Reminder lead days must be between 1 and 30");
		}
		this.leadDays = leadDays;
	}

	public long getPollIntervalMs() {
		return this.pollIntervalMs;
	}

	public void setPollIntervalMs(long pollIntervalMs) {
		this.pollIntervalMs = pollIntervalMs;
	}

}
