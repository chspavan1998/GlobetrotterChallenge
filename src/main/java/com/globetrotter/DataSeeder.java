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
        // Load JSON file from resources
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getResourceAsStream("/dataset.json");
        List<DestinationSeed> seeds = mapper.readValue(inputStream, new TypeReference<List<DestinationSeed>>() {});

        for (DestinationSeed seed : seeds) {
            // Check if a destination with this city already exists
            if (!destinationRepository.existsByName(seed.getCity())) {
                Destination destination = new Destination();
                destination.setName(seed.getCity());
                // Optionally, you can combine city and country or store country separately.
                destination.setClues(String.join(" | ", seed.getClues()));
                destination.setFunFacts(String.join(" | ", seed.getFun_fact()));
                destination.setTrivia(String.join(" | ", seed.getTrivia()));
                destinationRepository.save(destination);
            }
        }
        System.out.println("Data seeding completed. Total destinations: " + destinationRepository.count());
    }

    // Helper class matching the JSON structure
    static class DestinationSeed {
        private String city;
        private String country;
        private List<String> clues;
        private List<String> fun_fact;
        private List<String> trivia;

        // Getters and setters
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public List<String> getClues() { return clues; }
        public void setClues(List<String> clues) { this.clues = clues; }
        public List<String> getFun_fact() { return fun_fact; }
        public void setFun_fact(List<String> fun_fact) { this.fun_fact = fun_fact; }
        public List<String> getTrivia() { return trivia; }
        public void setTrivia(List<String> trivia) { this.trivia = trivia; }
    }
}
