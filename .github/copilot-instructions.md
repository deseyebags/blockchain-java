# Copilot Instructions for blockchain-java

## Project Overview

This is a **Spring Boot 3 REST API** that demonstrates a simplified blockchain implementation with proof-of-work (PoW) mining. It is a Java 21 Maven project using SHA-256 hashing and a difficulty-based mining algorithm.

## Technology Stack

| Component    | Details                             |
|--------------|-------------------------------------|
| Java         | 21                                  |
| Spring Boot  | 3.2.5                               |
| Build tool   | Apache Maven 3.9.11 (via wrapper)   |
| Testing      | JUnit 5, Mockito, Spring Test       |
| Container    | Docker (multi-stage, Alpine JRE 21) |
| CI/CD        | GitHub Actions (`.github/workflows/main.yml`) |

## Directory Structure

```
src/
  main/java/com/demo/blockchain/
    BlockchainApplication.java       # Spring Boot entry point
    controllers/BlockchainController.java  # REST controller (/api/blockchain)
    models/Block.java                # Block with SHA-256 hash & PoW mining
    models/Blockchain.java           # Chain management & validation
    services/BlockchainService.java  # Spring @Service wrapping Blockchain
  main/resources/application.properties  # spring.application.name=blockchain
  test/java/com/demo/blockchain/
    BlockchainApplicationTests.java  # Spring context integration test
    controllers/BlockchainControllerTest.java  # MockMvc controller tests
    models/BlockTest.java            # Block unit tests
    models/BlockchainTest.java       # Blockchain unit tests
    services/BlockchainServiceTest.java  # Service layer tests
pom.xml           # Maven config (groupId: com.demo, artifactId: blockchain)
Dockerfile        # Multi-stage Docker build
readme.md         # Setup & run instructions
```

## REST API

Base path: `/api/blockchain`  
Default port: **8080**

| Method | Path     | Description                                         |
|--------|----------|-----------------------------------------------------|
| GET    | /chain   | Returns all blocks in the chain as JSON             |
| POST   | /mine    | Mines a new block; request body = plain-text data   |
| GET    | /valid   | Returns `"VALID"` or `"INVALID"`                    |

## Common Commands

```bash
# Install dependencies & compile
mvn clean install

# Run all unit tests
mvn test

# Start the application (port 8080)
mvn spring-boot:run

# Build an executable JAR (skip tests)
mvn clean package -DskipTests

# Docker build & run
docker build -t blockchain-app:latest .
docker run --rm -p 8080:8080 blockchain-app:latest
```

Use the Maven wrapper (`./mvnw`) as a drop-in replacement for `mvn` to ensure a consistent Maven version (3.9.11).

## Key Design Details

- **Block**: `index`, `timestamp`, `data`, `previousHash`, `nonce`, `hash`. Hash is SHA-256 over all fields. Mining increments `nonce` until `hash` starts with `difficulty` leading zeros (default: **2**).
- **Blockchain**: `ArrayList<Block>` initialised with a genesis block. `addBlock(data)` throws `IllegalArgumentException` for blank/null data. `isValid()` re-computes every hash and checks the `previousHash` linkage.
- **BlockchainService**: Spring singleton `@Service` that owns the `Blockchain` instance.
- **BlockchainController**: Constructor-injected `BlockchainService`; returns `ResponseEntity` with appropriate HTTP status codes.

## Testing Conventions

- Test classes mirror `src/main` package structure under `src/test`.
- Controller tests use `@WebMvcTest` + `MockMvc`.
- Service and model tests are plain JUnit 5 / Mockito tests (`@ExtendWith(MockitoExtension.class)`).
- Test method naming: `test<MethodName><Scenario>()` or descriptive snake_case.
- New tests should follow the same pattern as existing tests in the same package.

## CI/CD

GitHub Actions workflow (`.github/workflows/main.yml`) runs on every push to `main`/`develop` and on pull requests:
1. Checks out the code.
2. Sets up JDK 21 (Temurin).
3. Runs `mvn test`.

Ensure `mvn test` passes locally before opening a pull request.

## Known Issues & Workarounds

- **Docker Maven version mismatch**: The `Dockerfile` uses `maven:3.10.1-eclipse-temurin-21` for the build stage while the wrapper targets 3.9.11. If the Docker build fails due to a Maven version incompatibility, update the `FROM` image in `Dockerfile` to `maven:3.9.11-eclipse-temurin-21` or use `./mvnw` inside the container.
- **Tests skipped in Docker**: The Docker build runs `mvn package -DskipTests` intentionally; tests are expected to run only in CI via `mvn test`.

## Coding Conventions

- Package: `com.demo.blockchain.{models,services,controllers}`
- Follow existing Spring annotation style (`@Service`, `@RestController`, `@RequestMapping`).
- Use constructor injection (not field injection) for Spring beans, as done in `BlockchainController`.
- Validate inputs in the model layer; propagate exceptions to the controller for `ResponseEntity` error responses.
- Use `StringBuilder` for hex-string construction (see `Block.calculateHash()`).
