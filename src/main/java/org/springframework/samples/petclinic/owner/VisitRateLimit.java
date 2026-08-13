/*
 * Copyright 2012-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "visit_rate_limits")
class VisitRateLimit {

	@Id
	@Column(name = "pet_id")
	private Integer petId;

	@Column(name = "window_started_at", nullable = false)
	private Instant windowStartedAt;

	@Column(name = "attempt_count", nullable = false)
	private int attemptCount;

	protected VisitRateLimit() {
	}

	VisitRateLimit(Integer petId, Instant windowStartedAt) {
		this.petId = petId;
		this.windowStartedAt = windowStartedAt;
		this.attemptCount = 1;
	}

	Instant getWindowStartedAt() {
		return this.windowStartedAt;
	}

	int getAttemptCount() {
		return this.attemptCount;
	}

	void reset(Instant now) {
		this.windowStartedAt = now;
		this.attemptCount = 1;
	}

	void recordAttempt() {
		this.attemptCount++;
	}

}
