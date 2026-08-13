/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import java.time.LocalDate;

public record ReminderMessage(Integer ownerId, String destination, String petName, LocalDate visitDate) {
}
