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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class VisitRateLimiterIntegrationTests {

	private static final int TEST_PET_ID = 1;

	@Autowired
	private VisitRateLimiter rateLimiter;

	@Autowired
	private VisitRateLimitRepository limits;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void clearLimits() {
		this.limits.deleteAll();
	}

	@Test
	void rejectsTheFourthAttemptAndReportsTheRemainingWindow() {
		Instant now = Instant.parse("2026-07-28T00:00:00Z");

		assertThat(this.rateLimiter.acquire(TEST_PET_ID, now).allowed()).isTrue();
		assertThat(this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(1)).allowed()).isTrue();
		assertThat(this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(2)).allowed()).isTrue();

		VisitRateLimiter.Decision rejected = this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(3));
		assertThat(rejected.allowed()).isFalse();
		assertThat(rejected.retryAfter()).hasSeconds(57);
	}

	@Test
	void resetsAtTheExactWindowBoundary() {
		Instant now = Instant.parse("2026-07-28T00:00:00Z");
		this.rateLimiter.acquire(TEST_PET_ID, now);
		this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(1));
		this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(2));

		assertThat(this.rateLimiter.acquire(TEST_PET_ID, now.plusSeconds(60)).allowed()).isTrue();
		assertThat(this.limits.findById(TEST_PET_ID)).get().extracting(VisitRateLimit::getAttemptCount).isEqualTo(1);
	}

	@Test
	void fourthMatchingPostReturns429WithRetryAfter() throws Exception {
		String path = "/owners/1/pets/1/visits/new";

		for (int attempt = 0; attempt < VisitRateLimiter.MAX_ATTEMPTS; attempt++) {
			this.mockMvc.perform(post(path)).andExpect(status().isOk());
		}

		this.mockMvc.perform(post(path))
			.andExpect(status().isTooManyRequests())
			.andExpect(header().exists(HttpHeaders.RETRY_AFTER))
			.andExpect(content().string("Too many visit booking attempts for this pet. Try again later."));
	}

	@RepeatedTest(5)
	void allowsExactlyThreeOfEightConcurrentAttempts() throws Exception {
		int requestCount = 8;
		CountDownLatch ready = new CountDownLatch(requestCount);
		CountDownLatch start = new CountDownLatch(1);
		ExecutorService executor = Executors.newFixedThreadPool(requestCount);
		List<Future<Boolean>> outcomes = new ArrayList<>();
		Instant now = Instant.parse("2026-07-28T00:00:00Z");

		try {
			for (int index = 0; index < requestCount; index++) {
				outcomes.add(executor.submit(() -> {
					ready.countDown();
					start.await();
					return this.rateLimiter.acquire(TEST_PET_ID, now).allowed();
				}));
			}

			assertThat(ready.await(5, TimeUnit.SECONDS)).isTrue();
			start.countDown();

			long allowed = 0;
			for (Future<Boolean> outcome : outcomes) {
				if (outcome.get(5, TimeUnit.SECONDS)) {
					allowed++;
				}
			}
			assertThat(allowed).isEqualTo(VisitRateLimiter.MAX_ATTEMPTS);
		}
		finally {
			executor.shutdownNow();
		}
	}

}
