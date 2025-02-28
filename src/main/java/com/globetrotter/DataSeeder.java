package com.globetrotter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globetrotter.model.Destination;
import com.globetrotter.repository.DestinationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DestinationRepository destinationRepository;

    public DataSeeder(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (destinationRepository.count() == 0) {
            // Load JSON file from resources
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/destinations.json");
            List<Destination> destinations = mapper.readValue(inputStream, new TypeReference<List<Destination>>(){});
            destinationRepository.saveAll(destinations);
            System.out.println("Destinations loaded: " + destinations.size());
        }
    }
}
