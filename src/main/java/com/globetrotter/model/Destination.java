package com.globetrotter.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    // For simplicity, store clues, fun facts, and trivia as comma-separated strings.
    @Column(length = 1024)
    private String clues; // e.g., "clue1,clue2"

    @Column(length = 1024)
    private String funFacts; // e.g., "fact1,fact2"

    @Column(length = 1024)
    private String trivia; // e.g., "trivia1,trivia2"
}
