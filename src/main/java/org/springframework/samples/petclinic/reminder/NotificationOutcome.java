/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

/**
 * Evidence returned by a notification adapter.
 */
public enum NotificationOutcome {

	/**
	 * The configured adapter accepted the reminder. This does not prove user receipt.
	 */
	ACCEPTED,

	/**
	 * The adapter could not determine whether the external effect occurred.
	 */
	UNKNOWN

}
