/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReminderController.class)
@DisabledInNativeImage
@DisabledInAotMode
class ReminderControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ReminderService reminders;

	@MockitoBean
	private ReminderProperties properties;

	@Test
	void displaysReminderOperationsPage() throws Exception {
		given(this.reminders.findAll()).willReturn(List.of());
		given(this.properties.getLeadDays()).willReturn(2);
		given(this.properties.isEnabled()).willReturn(true);

		this.mockMvc.perform(get("/reminders"))
			.andExpect(status().isOk())
			.andExpect(view().name("reminders/reminderList"))
			.andExpect(model().attribute("leadDays", 2));
	}

	@Test
	void reportsManualProcessingResult() throws Exception {
		given(this.reminders.processDueReminders()).willReturn(new ReminderRun(1, 1, 1, 0, 0, 0));

		this.mockMvc.perform(post("/reminders/process"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/reminders"))
			.andExpect(flash().attribute("message",
					"Reminder run: 1 created, 1 accepted, 0 failed, 0 unknown, 0 already recorded"));
	}

}
