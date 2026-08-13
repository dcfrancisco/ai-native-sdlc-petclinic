/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<ReminderRecord, Integer> {

	Optional<ReminderRecord> findByVisitId(Integer visitId);

	List<ReminderRecord> findAllByOrderByCreatedAtDesc();

}
