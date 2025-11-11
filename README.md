# Mirror Repo Analysis

Mirror Repo Analysis is Brown CCV's repository analysis platform that visualizes and analyzes GitHub repository data for the organization. It consists of a Spring Boot backend (`Repo-DB`) and a React frontend (`Repo-Visualization`).

## Table of Contents
- [Mirror Repo Analysis](#mirror-repo-analysis)
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Demo](#demo)
- [Credits](#credits)
- [Troubleshooting](#troubleshooting)
- [License](#license)

## Overview

This full-stack application provides tools to:
- Fetch and store GitHub repository data from the Brown CCV organization
- Analyze and filter repository information
- Visualize repository metrics and insights through an interactive web interface

## Project Structure

```
mirror-repo-analysis/
├── backend/
│   └── repo-db/              # Spring Boot backend API
├── frontend/
│   └── repo-visualization-app/  # React frontend application
└── README.md
```

## Requirements

### Backend
- [JDK 11](https://www.oracle.com/java/technologies/downloads/#jdk21-mac) and above
- [Maven 3.9.9](https://maven.apache.org/download.cgi)
- [MongoDB Atlas cloud](https://www.mongodb.com/products/platform/atlas-database) or [Local MongoDB server](https://www.mongodb.com/try/download/community)

### Frontend
- [NodeJS v22.11.0](https://nodejs.org/en/download/source-code)

## Quick Start

### 1. Clone and Navigate
```bash
git clone <repository-url>
cd mirror-repo-analysis
```

### 2. Start the Backend
```bash
cd backend/repo-db
./mvnw spring-boot:run
```
The backend will run on `http://localhost:8080/`

### 3. Start the Frontend
In a new terminal:
```bash
cd frontend/repo-visualization-app
npm install
npm start
```
The frontend will run on `http://localhost:3000/`

## Configuration

### Backend Configuration
See [backend/repo-db/README.md](backend/repo-db/README.md) for detailed MongoDB setup instructions (Cloud Atlas or Local).

**Default connection** uses MongoDB Atlas cloud (already configured). Update `backend/repo-db/src/main/resources/application.properties` if needed:
```properties
spring.data.mongodb.uri=mongodb+srv://<USERNAME>:<PASSWORD>@cluster0.kmvmf.mongodb.net/?retryWrites=true&w=majority&appName=<DATABASE_NAME>
spring.data.mongodb.database=DATABASE_NAME
```

### Frontend Configuration
Create `frontend/repo-visualization-app/.env`:
```properties
REACT_APP_API_URL="http://localhost:8080/"
```

## Running the Application

### Development Mode
Terminal 1 - Backend:
```bash
cd backend/repo-db
./mvnw spring-boot:run
```

Terminal 2 - Frontend:
```bash
cd frontend/repo-visualization-app
npm start
```

### Production Build
Backend:
```bash
cd backend/repo-db
./mvnw clean package
java -jar target/your-application-name-version.jar
```

Frontend:
```bash
cd frontend/repo-visualization-app
npm run build
```

## API Documentation

### Base URL
`http://localhost:8080/`

### Available Endpoints
1. **`/load-github-repos`**
   - Loads repository data from GitHub API (`https://api.github.com/orgs/brown-ccv/repos`) into the database
   
2. **`/fetch-repos/filter`**
   - Fetches and filters repository data from the database

For detailed API information, see [backend/repo-db/README.md](backend/repo-db/README.md#apis)

## Demo

Watch the application in action: [Mirror Repo Analysis Demo](https://www.youtube.com/watch?v=rsfLrRiYjdQ)

## Credits
- [MongoDB Atlas](https://www.mongodb.com/atlas) for database hosting
- [GitHub API](https://docs.github.com/en/rest) for repository data
- [Spring Boot](https://spring.io/projects/spring-boot) for the backend framework
- [React](https://reactjs.org/) for the frontend framework
- [Node.js](https://nodejs.org/) for the runtime environment

## Troubleshooting

### General Issues
- Ensure both backend and frontend are running before accessing the application
- Check that ports 8080 (backend) and 3000 (frontend) are not in use

### Backend Issues
See [backend/repo-db/README.md#troubleshooting-tips](backend/repo-db/README.md#troubleshooting-tips)

### Frontend Issues
See [frontend/repo-visualization-app/README.md#troubleshooting-tips](frontend/repo-visualization-app/README.md#troubleshooting-tips)

## License

[MIT](https://choosealicense.com/licenses/mit/)