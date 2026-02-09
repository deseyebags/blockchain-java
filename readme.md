# 1. Install dependencies

```
mvn clean install
```

# 2. Run the app (development)

```
mvn spring-boot:run
```

# OR run in one command (installs + runs)

```
mvn clean spring-boot:run
```

## Docker

Build the image (uses the project's `Dockerfile`):

```bash
docker build -t blockchain-app:latest .
```

Run the container and expose port 8080:

```bash
docker run --rm -p 8080:8080 blockchain-app:latest
```

The `Dockerfile` performs a multi-stage build: it packages the app with Maven and copies the executable jar into a slim JRE image.
