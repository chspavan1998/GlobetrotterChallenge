package com.globetrotter.controller;

import com.globetrotter.model.Destination;
import com.globetrotter.model.GameSession;
import com.globetrotter.repository.DestinationRepository;
import com.globetrotter.repository.GameSessionRepository;
import com.globetrotter.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    // 30-second time limit in milliseconds.
    private static final long TIME_LIMIT_MILLIS = 30000;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    /**
     * Start a new game session.
     * Returns the sessionId, the clues for the destination, and the time limit in seconds.
     */
    @GetMapping("/start")
    public ResponseEntity<GameSessionResponse> startGame() {
        // Select a random destination.
        Destination destination = destinationService.getRandomDestination();

        // Generate a unique session ID.
        String sessionId = UUID.randomUUID().toString();

        // Save the session to the database.
        GameSession session = new GameSession(sessionId, destination.getId(), Instant.now());
        gameSessionRepository.save(session);

        // Build the response.
        GameSessionResponse response = new GameSessionResponse(sessionId, destination.getClues(), TIME_LIMIT_MILLIS / 1000);
        return ResponseEntity.ok(response);
    }

    /**
     * Submit an answer for an ongoing game session.
     */
    @PostMapping("/submit")
    public ResponseEntity<GameResultResponse> submitAnswer(
            @RequestParam String sessionId,
            @RequestParam String answer) {

        Optional<GameSession> optionalSession = gameSessionRepository.findById(sessionId);
        if (!optionalSession.isPresent()) {
            return ResponseEntity.badRequest().body(new GameResultResponse(false, "Invalid or expired session ID."));
        }
        GameSession session = optionalSession.get();

        long elapsed = Instant.now().toEpochMilli() - session.getStartTime().toEpochMilli();
        if (elapsed > TIME_LIMIT_MILLIS) {
            gameSessionRepository.deleteById(sessionId);
            return ResponseEntity.ok(new GameResultResponse(false, "Time's up!"));
        }

        // Retrieve the destination using the stored destinationId.
        Optional<Destination> optionalDestination = destinationRepository.findById(session.getDestinationId());
        if (!optionalDestination.isPresent()) {
            gameSessionRepository.deleteById(sessionId);
            return ResponseEntity.badRequest().body(new GameResultResponse(false, "Destination not found for session."));
        }
        Destination destination = optionalDestination.get();

        boolean isCorrect = destinationService.validateAnswer(destination, answer);
        gameSessionRepository.deleteById(sessionId); // Clean up the session
        String message = isCorrect ? "Correct answer!" : "Incorrect answer.";
        return ResponseEntity.ok(new GameResultResponse(isCorrect, message));
    }

    // DTO for the game session start response.
    public static class GameSessionResponse {
        public String sessionId;
        public String clues;
        public long timeLimitSeconds;

        public GameSessionResponse(String sessionId, String clues, long timeLimitSeconds) {
            this.sessionId = sessionId;
            this.clues = clues;
            this.timeLimitSeconds = timeLimitSeconds;
        }
    }

    // DTO for the game result response.
    public static class GameResultResponse {
        public boolean correct;
        public String message;

        public GameResultResponse(boolean correct, String message) {
            this.correct = correct;
            this.message = message;
        }
    }
}
