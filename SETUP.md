# Project Setup

## Prerequisites

- Java 25
- Maven
- PostgreSQL
- Google GenAI API key

---

## PostgreSQL Setup

Run PostgreSQL locally or using Docker/Podman.

Default configuration:

```text
Database: db
Username: user
Password: pass
Port: 5432
```

---

## Google GenAI API Key Setup

1. Visit:

https://aistudio.google.com/app/apikey

2. Sign in with your Google account

3. Create a new API key

4. Add it to environment variables:

```bash
GOOGLE_GENAI_API_KEY=your_api_key
```

---

## Google OAuth Setup

1. Open Google Cloud Console:

https://console.cloud.google.com/

2. Create a new project

3. Enable Google OAuth APIs

4. Configure OAuth consent screen

5. Create OAuth Client ID and Secret

6. Add them to environment variables:

```bash
GOOGLE_CLIENT_ID=your_client_id
GOOGLE_CLIENT_SECRET=your_client_secret
```

---

## Running Services

Open a separate terminal for each service before running them.

Start services in this order:

1. PostgreSQL
2. auth
3. problems-service
4. mail-service
5. ai-service
6. gateway
7. batch-service

Example:

```bash
cd auth
./mvnw spring-boot:run
```