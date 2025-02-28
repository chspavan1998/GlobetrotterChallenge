Globetrotter Challenge
======================

Globetrotter Challenge is a full-stack web application that challenges users to guess famous travel destinations based on cryptic clues. Once a destination is guessed correctly, users unlock fun facts, trivia, and surprises about the location.

Table of Contents
-----------------
1. Features
2. Tech Stack
3. Setup and Installation
4. API Endpoints
5. Testing
6. Deployment
7. Future Enhancements
8. License

Features
--------
- Random Destination: Retrieve a random destination with clues, fun facts, and trivia.
- Answer Validation: Validate user guesses and provide immediate feedback.
- User Registration & Scoring: Register users and track their correct/incorrect answers.
- Challenge a Friend: Generate unique invitation links that display the inviter’s score.
- Interactive API Documentation: Swagger-powered API docs for easy endpoint testing and exploration.

Tech Stack
----------
- Backend: Java, Spring Boot, Spring Data JPA
- Database: H2 (for development; switch to PostgreSQL or another DB for production)
- API Documentation: springdoc-openapi (Swagger UI)
- Build Tool: Maven

Setup and Installation
----------------------
1. Clone the Repository:
   git clone https://github.com/chspavan1998/GlobetrotterChallenge.git
   cd GlobetrotterChallenge

2. Build the Project:
   mvn clean package

3. Run the Application:
   mvn spring-boot:run

4. Access the Application:
   - Random Destination Endpoint: http://localhost:8080/destinations/random
   - Swagger UI: http://localhost:8080/swagger-ui/index.html

API Endpoints
-------------
Destinations:
- GET /destinations/random
  Retrieves a random destination along with clues, fun facts, and trivia.
- POST /destinations/answer
  Validates the user's answer for a given destination.
  Parameters:
    - destinationId: ID of the destination
    - answer: The user's guess

User:
- POST /user/register
  Registers a new user.
  Parameters:
    - username: Unique username
- GET /user/{username}/score
  Retrieves the score for the specified user.
- GET /user/invite
  Generates an invitation link for the user, including a token and the user's current score.
  Query Parameter:
    - username: The inviter's username

Testing
-------
Run your tests using Maven:
   mvn test
This project includes unit and integration tests for key endpoints using JUnit and Spring Boot Test.

Deployment
----------
For production deployments, consider the following steps:
- Database: Switch from H2 to a production-grade database (e.g., PostgreSQL).
- Environment Variables: Configure environment variables for database credentials and other settings.
- Hosting Platforms: Deploy to platforms like Railway, Heroku, AWS, or similar.

Future Enhancements
-------------------
- Expand the dataset to include 100+ destinations by loading data from external files or AI-generated sources.
- Enhance the "Challenge a Friend" feature with token persistence and friend invitation tracking.
- Develop a front-end interface using React, Angular, or another framework.
- Add game modes such as timer-based challenges or image-based clues.
- Improve test coverage and add more integration tests.

License
-------
This project is licensed under the MIT License.
