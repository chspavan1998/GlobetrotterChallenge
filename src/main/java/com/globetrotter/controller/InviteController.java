package com.globetrotter.controller;

import com.globetrotter.model.User;
import com.globetrotter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InviteController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint for invited friends.
     * Returns the inviter's score based on the token.
     */
    @GetMapping("/invite")
    public ResponseEntity<InviteDetailsResponse> getInviteDetails(@RequestParam String token) {
        // Look up the inviter's username using the token from the in-memory store.
        String inviterUsername = UserController.getInvitationMap().get(token);
        if (inviterUsername == null) {
            return ResponseEntity.badRequest().body(new InviteDetailsResponse("Invalid or expired invitation token", 0, 0));
        }
        User inviter = userService.getUser(inviterUsername);
        if (inviter == null) {
            return ResponseEntity.badRequest().body(new InviteDetailsResponse("Inviter not found", 0, 0));
        }
        return ResponseEntity.ok(new InviteDetailsResponse("Invitation valid", inviter.getCorrectAnswers(), inviter.getIncorrectAnswers()));
    }

    // DTO for the invitation details response.
    static class InviteDetailsResponse {
        public String message;
        public int inviterCorrectScore;
        public int inviterIncorrectScore;
        
        public InviteDetailsResponse(String message, int inviterCorrectScore, int inviterIncorrectScore) {
            this.message = message;
            this.inviterCorrectScore = inviterCorrectScore;
            this.inviterIncorrectScore = inviterIncorrectScore;
        }
    }
}
