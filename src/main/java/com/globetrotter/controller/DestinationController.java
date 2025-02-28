package com.globetrotter.controller;

import com.globetrotter.model.Destination;
import com.globetrotter.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    // Endpoint to get a random destination with clues
    @GetMapping("/random")
    public ResponseEntity<Destination> getRandomDestination() {
        Destination destination = destinationService.getRandomDestination();
        // Note: For production, consider filtering out full answers/trivia as needed.
        return ResponseEntity.ok(destination);
    }

    // Endpoint to check the user's answer
    @PostMapping("/answer")
    public ResponseEntity<?> checkAnswer(@RequestParam Long destinationId, @RequestParam String answer) {
        // In a complete implementation, retrieve the destination by ID.
        // Here we're using a random destination for simplicity.
        Destination destination = destinationService.getRandomDestination(); // Replace with findById(destinationId)
        boolean isCorrect = destinationService.validateAnswer(destination, answer);
        // For this example, grab the first fun fact from the comma-separated list.
        String funFact = destination.getFunFacts().split(",")[0];
        return ResponseEntity.ok(new AnswerResponse(isCorrect, funFact));
    }
    
    // DTO for the answer response
    static class AnswerResponse {
        public boolean correct;
        public String funFact;

        public AnswerResponse(boolean correct, String funFact) {
            this.correct = correct;
            this.funFact = funFact;
        }
    }
}
