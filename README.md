# Leet Journal

Leet Journal is a Spring Boot microservices project for tracking LeetCode progress, managing problem metadata, authenticating users, sending mail, and experimenting with AI-assisted problem solving.

## Overview

The repository is organized as a set of independently runnable services:

- `auth` handles authentication and authorization server concerns.
- `gateway` is the public entry point and forwards requests to backend services.
- `problems-service` serves problem data and execution endpoints.
- `batch-service` is responsible for batch ingestion and scheduled-style processing.
- `mail-service` sends MIME email notifications.
- `ai-service` streams AI responses for DSA help.

## Architecture

The system follows a gateway-first microservice layout.

```mermaid
flowchart LR
	U[User / Frontend] --> G[Gateway :9000]
	G --> P[Problems Service :8001]
	G --> M[Mail Service :8002]
	G --> A[AI Service :8003]
	P --> DB[(PostgreSQL)]
	B[Batch Service] --> DB
	AU[Auth Service :8000] --> DB2[(PostgreSQL auth DB)]
	P -. JWT validation .-> AU
	G -. OAuth2 login .-> AU
	M --> SMTP[(SMTP / Gmail)]
	A --> GENAI[(Google Gemini)]
```

### Request flow

- The client talks to `gateway` on port `9000`.
- `gateway` relays authenticated requests to the downstream services.
- `/problems/**` and `/code/**` are forwarded to `problems-service` on port `8001`.
- `/mail/**` is forwarded to `mail-service` on port `8002`.
- `/ai/**` is forwarded to `ai-service` on port `8003`.
- `auth` runs on port `8000` and issues tokens consumed by the other services.

### Service responsibilities

- `auth` provides the OAuth2 authorization server and persists identity data in PostgreSQL.
- `problems-service` exposes endpoints for listing problems, fetching a problem by id, checking the current authenticated user, and running code locally.
- `batch-service` loads problem data from CSV and stores it in the database.
- `mail-service` sends email notifications using SMTP.
- `ai-service` wraps the Google GenAI client and streams chat-style responses.

## Repository Layout

Each service is a standalone Maven project:

- `auth/`
- `gateway/`
- `problems-service/`
- `batch-service/`
- `mail-service/`
- `ai-service/`

The services are intentionally separate so they can be developed, tested, and deployed independently.

## Prerequisites

- Java 25
- Maven Wrapper or Maven
- PostgreSQL
- SMTP credentials for mail delivery
- Google GenAI API key for the AI service

## Configuration

Common local defaults are defined in each service’s `application.yml` or `application.yaml` file.

Environment variables used by the project include:

- `GMAIL_MAIL_USERNAME`
- `GMAIL_MAIL_PASSWORD`
- `GOOGLE_GENAI_API_KEY`
- `AUTH_SERVER_URI` for overriding the auth server location used by the gateway

PostgreSQL connection defaults used by the services point to `jdbc:postgresql://localhost:5432/db` with the username `user` and password `pass`.

## Running Locally
You will need a PostgreSQL database running for the application. Best way to do this is to run a container using Docker / Podman and configuring your environment variables. By default the application expects the container on port 5432 with the following config:
Database name -> db
Username -> user
Password -> pass


Start the services in this order (to prevent unnecessary errors):

1. PostgreSQL Database (container or locally)
2. `auth`
3. `problems-service`
4. `mail-service`
5. `ai-service`
6. `gateway`
7. `batch-service` - only if you want to import data or run batch jobs

Use the Maven Wrapper from each service directory, for example:

```bash
cd auth
./mvnw spring-boot:run
```

Repeat the same pattern for the other services.

## Useful Endpoints

- `GET http://localhost:9000/problems/all`
- `GET http://localhost:9000/problems/find/{id}`
- `GET http://localhost:9000/code/run`
- `GET http://localhost:9000/mail/mime?to=...&subject=...&text=...`
- `GET http://localhost:9000/ai/chat?prompt=...`

## Contributing

Thank you for your interest in contributing to Leet Journal!

### Before Contributing

- Check existing issues before opening a new one.
- For larger changes, open an issue first to discuss the approach.
- Beginners are encouraged to start with documentation or small fixes.

---

### Google GenAI API Key Setup

The `ai-service` requires a Google GenAI API key to enable AI-powered DSA assistance.

### Steps to get the API key

1. Visit Google AI Studio:

https://aistudio.google.com/app/apikey

2. Sign in with your Google account.

3. Create a new API key.

4. Add the API key to your environment variables:

```bash
GOOGLE_GENAI_API_KEY=your_api_key
```

5. Restart the `ai-service` after updating the environment variable.

---

### Contribution Workflow

1. Fork the repository

2. Clone your fork locally

```bash
git clone https://github.com/your-username/leet-journal.git
```

3. Create a new branch

```bash
git checkout -b feature-name
```

4. Make your changes

5. Commit your changes

```bash
git commit -m "Describe your changes"
```

6. Push your branch

```bash
git push origin feature-name
```

7. Open a Pull Request with a clear description of your changes

---

### Pull Request Guidelines

- Keep pull requests small and focused.
- Write clear commit messages and PR descriptions.
- Update documentation when setup or behavior changes.
- Test your changes whenever practical before opening a PR.

---

### Coding Conventions

- Follow the existing Spring Boot structure.
- Keep controllers lightweight.
- Move business logic into service classes.
- Avoid unnecessary dependencies.

## Project Status
This project is under active development and is intended to grow as an open source learning platform for LeetCode practice and DSA tooling.