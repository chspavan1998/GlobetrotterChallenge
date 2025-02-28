package com.globetrotter.service;

import com.globetrotter.model.Destination;
import com.globetrotter.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;
    
    private final Random random = new Random();

    public Destination getRandomDestination() {
        List<Destination> destinations = destinationRepository.findAll();
        if(destinations.isEmpty()) {
            throw new RuntimeException("No destinations found in the database");
        }
        int index = random.nextInt(destinations.size());
        return destinations.get(index);
    }
    
    public boolean validateAnswer(Destination destination, String userAnswer) {
        // Basic validation: case-insensitive comparison
        return destination.getName().equalsIgnoreCase(userAnswer.trim());
    }
}
