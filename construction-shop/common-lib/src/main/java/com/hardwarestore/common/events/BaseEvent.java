package com.hardwarestore.common.events;
import java.time.Instant;
import java.util.UUID;

public abstract class BaseEvent {

    private final String eventId;
    private final Instant createdAt;

    protected BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}