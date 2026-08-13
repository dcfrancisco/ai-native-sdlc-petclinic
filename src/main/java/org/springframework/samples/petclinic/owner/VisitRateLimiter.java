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

import java.time.Duration;
import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class VisitRateLimiter {

	static final int MAX_ATTEMPTS = 3;

	static final Duration WINDOW = Duration.ofSeconds(60);

	private final PetRepository pets;

	private final VisitRateLimitRepository limits;

	VisitRateLimiter(PetRepository pets, VisitRateLimitRepository limits) {
		this.pets = pets;
		this.limits = limits;
	}

	@Transactional
	Decision acquire(int petId, Instant now) {
		if (this.pets.findByIdForUpdate(petId).isEmpty()) {
			return Decision.permit();
		}

		VisitRateLimit limit = this.limits.findById(petId).orElse(null);
		if (limit == null) {
			this.limits.save(new VisitRateLimit(petId, now));
			return Decision.permit();
		}

		Instant resetAt = limit.getWindowStartedAt().plus(WINDOW);
		if (!now.isBefore(resetAt)) {
			limit.reset(now);
			return Decision.permit();
		}

		if (limit.getAttemptCount() >= MAX_ATTEMPTS) {
			return Decision.deny(Duration.between(now, resetAt));
		}

		limit.recordAttempt();
		return Decision.permit();
	}

	record Decision(boolean allowed, Duration retryAfter) {

		static Decision permit() {
			return new Decision(true, Duration.ZERO);
		}

		static Decision deny(Duration retryAfter) {
			return new Decision(false, retryAfter);
		}

	}

}
