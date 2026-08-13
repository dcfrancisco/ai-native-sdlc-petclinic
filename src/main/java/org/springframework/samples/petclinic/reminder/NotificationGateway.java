/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

public interface NotificationGateway {

	NotificationOutcome send(ReminderMessage message);

}
