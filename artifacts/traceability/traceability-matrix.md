# Reminder Traceability Matrix

**Chapter:** 12
**Status:** Reviewed

| Requirement | Implementation | Evidence |
|---|---|---|
| R1 | `ReminderService.processDueReminders` | `ReminderServiceTests`, `ReminderIntegrationTests` |
| R2 | Unique `visit_id`, repository lookup | Idempotency unit and integration tests |
| R3 | `NotificationGateway`, `LocalNotificationGateway` | Service and startup checks |
| R4 | `FAILED` state and `retry` | Failure unit test and operator procedure |
| R5 | `reminderList.html`, masked hint | MVC test and browser procedure |
| R6 | `ReminderScheduler`, `ReminderController` | Context startup and MVC test |
| R7 | `ReminderMetrics` | Actuator metric procedure |
| QR1 | Bounded reminder package | Full regression suite |
| QR2 | Unique constraint and existing lookup | Repeated processing tests |
| QR3 | Masking and safe error handling | Unit test and security review |
| QR4 | Three schema variants | H2, PostgreSQL, and MySQL checks |
| QR5 | Local adapter | Test suite without provider credentials |
