package com.globetrotter.controller;

import com.globetrotter.model.User;
import com.globetrotter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    // Existing endpoints for register and score...
    
    // Endpoint to generate an invitation link
    @GetMapping("/invite")
    public ResponseEntity<InviteResponse> getInviteLink(@RequestParam String username) {
        User user = userService.getUser(username);
        // Generate a random invitation token
        String token = UUID.randomUUID().toString();
        // Construct a dummy invite link (in a real app, the domain should be your actual URL)
        String inviteLink = "http://localhost:8080/invite?token=" + token + "&inviter=" + username;
        InviteResponse response = new InviteResponse(inviteLink, user.getCorrectAnswers(), user.getIncorrectAnswers());
        return ResponseEntity.ok(response);
    }

    // DTO for the invitation response
    static class InviteResponse {
        public String inviteLink;
        public int correctScore;
        public int incorrectScore;

        public InviteResponse(String inviteLink, int correctScore, int incorrectScore) {
            this.inviteLink = inviteLink;
            this.correctScore = correctScore;
            this.incorrectScore = incorrectScore;
        }
    }
}
