/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

public record ReminderRun(int eligible, int created, int accepted, int failed, int unknown, int existing) {
}
