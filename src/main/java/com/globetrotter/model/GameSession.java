package com.globetrotter.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "game_sessions")
public class GameSession {

    @Id
    private String sessionId; // Use the unique session ID as the primary key

    private Long destinationId;
    private Instant startTime;

    public GameSession() {
    }

    public GameSession(String sessionId, Long destinationId, Instant startTime) {
        this.sessionId = sessionId;
        this.destinationId = destinationId;
        this.startTime = startTime;
    }

    // Getters and setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
}
