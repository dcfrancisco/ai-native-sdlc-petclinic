# Reminder Architecture

**Chapter:** 6  
**Status:** Accepted for the local reference implementation

The reminder capability is a bounded package inside the PetClinic application. It reads accepted owner and visit data, applies reminder eligibility, persists a delivery record, calls a notification port, exposes an operator interface, and publishes metrics.

The local adapter logs a masked destination. It is a testable boundary, not a production provider simulation.

## Components

- `ReminderScheduler`: periodic trigger with configurable delay;
- `ReminderController`: operator list, manual processing, and retry;
- `ReminderService`: eligibility, idempotency, delivery, failure recording, and recovery;
- `ReminderRepository`: durable reminder state;
- `NotificationGateway`: outbound delivery port;
- `LocalNotificationGateway`: safe local adapter;
- `ReminderMetrics`: created, accepted, failed, and unknown counters.

## Consequential boundaries

- Owner contact data enters the reminder service but only a masked hint is persisted.
- Visit ID uniqueness provides the local idempotency boundary.
- Scheduling invokes domain behavior but does not own product policy.
- Metrics describe process outcomes, not user receipt.

See the context and component Mermaid sources in this directory.
