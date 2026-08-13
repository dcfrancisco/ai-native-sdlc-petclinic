/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class LocalNotificationGateway implements NotificationGateway {

	private static final Logger logger = LoggerFactory.getLogger(LocalNotificationGateway.class);

	@Override
	public NotificationOutcome send(ReminderMessage message) {
		logger.info("Local reminder accepted for owner {} and pet {} on {} to {}", message.ownerId(), message.petName(),
				message.visitDate(), mask(message.destination()));
		return NotificationOutcome.ACCEPTED;
	}

	private String mask(String destination) {
		if (destination == null || destination.length() < 4) {
			return "hidden";
		}
		return "******" + destination.substring(destination.length() - 4);
	}

}
