/*
 * Licensed under the Apache License, Version 2.0.
 */
package org.springframework.samples.petclinic.reminder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
class ReminderController {

	private final ReminderService reminders;

	private final ReminderProperties properties;

	ReminderController(ReminderService reminders, ReminderProperties properties) {
		this.reminders = reminders;
		this.properties = properties;
	}

	@GetMapping("/reminders")
	String reminders(Model model) {
		model.addAttribute("reminders", this.reminders.findAll());
		model.addAttribute("leadDays", this.properties.getLeadDays());
		model.addAttribute("enabled", this.properties.isEnabled());
		return "reminders/reminderList";
	}

	@PostMapping("/reminders/process")
	String process(RedirectAttributes redirect) {
		ReminderRun run = this.reminders.processDueReminders();
		redirect.addFlashAttribute("message",
				"Reminder run: " + run.created() + " created, " + run.accepted() + " accepted, " + run.failed()
						+ " failed, " + run.unknown() + " unknown, " + run.existing() + " already recorded");
		return "redirect:/reminders";
	}

	@PostMapping("/reminders/{id}/retry")
	String retry(@PathVariable Integer id, RedirectAttributes redirect) {
		ReminderStatus status = this.reminders.retry(id);
		String message = switch (status) {
			case ACCEPTED -> "Reminder accepted by local adapter";
			case FAILED -> "Reminder retry failed";
			case UNKNOWN -> "Reminder retry outcome is unknown; reconcile before another attempt";
			default -> "Only failed reminders can be retried; current status is " + status;
		};
		redirect.addFlashAttribute("message", message);
		return "redirect:/reminders";
	}

}
