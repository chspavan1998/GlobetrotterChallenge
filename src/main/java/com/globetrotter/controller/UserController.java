package com.globetrotter.controller;

import com.globetrotter.model.User;
import com.globetrotter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // In-memory store for invitations: token -> inviter username.
    private static final ConcurrentHashMap<String, String> invitationMap = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username) {
        User user = userService.registerUser(username);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/{username}/score")
    public ResponseEntity<ScoreResponse> getUserScore(@PathVariable String username) {
        User user = userService.getUser(username);
        return ResponseEntity.ok(new ScoreResponse(user.getCorrectAnswers(), user.getIncorrectAnswers()));
    }
    
    /**
     * Generate an invitation link for the given username.
     * The invitation link can be shared via WhatsApp.
     */
    @GetMapping("/invite")
    public ResponseEntity<InviteResponse> generateInvite(@RequestParam String username) {
        // Verify the user exists.
        User user = userService.getUser(username);
        if (user == null) {
            return ResponseEntity.badRequest().body(new InviteResponse("User not found", null, null, 0, 0));
        }
        // Generate a unique token.
        String token = UUID.randomUUID().toString();
        invitationMap.put(token, username);
        
        // Base invitation link (update the domain if deployed to production).
        String baseLink = "http://localhost:8080/invite?token=" + token;
        
        // Create a message including the inviter's score.
        String shareMessage = "Join me on Globetrotter Challenge! My score - Correct: " + 
                user.getCorrectAnswers() + ", Incorrect: " + user.getIncorrectAnswers() + 
                ". Play now: " + baseLink;
        
        // Generate a WhatsApp share link with URL encoding.
        String whatsappLink = "https://api.whatsapp.com/send?text=" + 
                URLEncoder.encode(shareMessage, StandardCharsets.UTF_8);
        
        InviteResponse response = new InviteResponse(
                "Invitation generated",
                baseLink,
                whatsappLink,
                user.getCorrectAnswers(),
                user.getIncorrectAnswers()
        );
        return ResponseEntity.ok(response);
    }
    
    // DTO for the score response.
    static class ScoreResponse {
        public int correct;
        public int incorrect;
        
        public ScoreResponse(int correct, int incorrect) {
            this.correct = correct;
            this.incorrect = incorrect;
        }
    }
    
    // DTO for the invitation response.
    static class InviteResponse {
        public String message;
        public String inviteLink;
        public String whatsappLink;
        public int inviterCorrectScore;
        public int inviterIncorrectScore;
        
        public InviteResponse(String message, String inviteLink, String whatsappLink, int inviterCorrectScore, int inviterIncorrectScore) {
            this.message = message;
            this.inviteLink = inviteLink;
            this.whatsappLink = whatsappLink;
            this.inviterCorrectScore = inviterCorrectScore;
            this.inviterIncorrectScore = inviterIncorrectScore;
        }
    }
    
    // Expose the invitation map for the InviteController to use.
    public static ConcurrentHashMap<String, String> getInvitationMap() {
        return invitationMap;
    }
}
