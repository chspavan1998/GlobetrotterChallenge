package com.globetrotter.repository;

import com.globetrotter.model.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, String> {
    // No additional methods are required for basic CRUD operations.
}
