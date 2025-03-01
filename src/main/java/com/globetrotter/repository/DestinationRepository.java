package com.globetrotter.repository;

import com.globetrotter.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    // Add custom queries if needed
    boolean existsByName(String name);
}
