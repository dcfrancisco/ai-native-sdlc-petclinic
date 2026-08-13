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

import java.io.IOException;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ConditionalOnBean(VisitRateLimiter.class)
class VisitRateLimiterFilter extends OncePerRequestFilter {

	private static final Pattern VISIT_POST = Pattern.compile("^/owners/\\d+/pets/(?<petId>\\d+)/visits/new/?$");

	private final VisitRateLimiter rateLimiter;

	VisitRateLimiterFilter(VisitRateLimiter rateLimiter) {
		this.rateLimiter = rateLimiter;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Matcher matcher = VISIT_POST.matcher(request.getRequestURI().substring(request.getContextPath().length()));
		if (!HttpMethod.POST.matches(request.getMethod()) || !matcher.matches()) {
			filterChain.doFilter(request, response);
			return;
		}

		int petId = Integer.parseInt(matcher.group("petId"));
		VisitRateLimiter.Decision decision = this.rateLimiter.acquire(petId, Instant.now());
		if (decision.allowed()) {
			filterChain.doFilter(request, response);
			return;
		}

		long retryAfterSeconds = Math.max(1, decision.retryAfter().toSeconds());
		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		response.setHeader(HttpHeaders.RETRY_AFTER, Long.toString(retryAfterSeconds));
		response.setContentType("text/plain");
		response.getWriter().write("Too many visit booking attempts for this pet. Try again later.");
	}

}
