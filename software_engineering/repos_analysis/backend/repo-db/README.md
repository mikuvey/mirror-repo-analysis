# Repo-DB

Repo-DB is the backend application for Brown CCV's repository analysis app. It handles data storage, retrieval, and analysis of GitHub repositories for the organization. The application connects with MongoDB Atlas cloud for data persistence and provides REST APIs for interaction.

## Table of Contents
- [Repo-DB](#repo-db)
- [Requirements](#requirements)
- [Configuration](#configuration)
  - [Connect MongoDB Atlas (Cloud Server)](#connect-mongodb-atlas-cloud-server)
  - [Steps to Configure MongoDB Locally](#steps-to-configure-mongodb-locally)
- [Installation](#installation)
- [APIs](#apis)
- [Credits](#credits)
- [Troubleshooting Tips](#troubleshooting-tips)
- [License](#license)


## Requirements
For building and running the application you need:
1. [JDK 11](https://www.oracle.com/java/technologies/downloads/#jdk21-mac) and above
2. [Maven 3.9.9](https://maven.apache.org/download.cgi)
3. [MongoDB Atlas cloud](https://www.mongodb.com/products/platform/atlas-database) or [Local server](https://www.mongodb.com/try/download/community)

## Configuration
### Connect MongoDB Atlas (cloud server)
Update the `application.properties` file with the following for configuring your own atlas cloud:

```properties

spring.data.mongodb.uri=mongodb+srv://<USERNAME>:<PASSWORD>@cluster0.kmvmf.mongodb.net/?retryWrites=true&w=majority&appName=<DATABASE_NAME>
spring.data.mongodb.database=DATABASE_NAME
search.index=Custom cloud db search index
```
* Replace < USERNAME >, < PASSWORD >, and < DATABASE_NAME > with your credentials and database name.
* Check this [video](https://www.youtube.com/watch?v=8kRlJbElFS8) to configure autocomplete search indexing on cloud.

>Note: Would recommend using cloud server as local DB hasn't been tested at this point of time. For easy setup don't change application.properties as it has been already configured for my cloud mongoDB server.

### Steps to Configure MongoDB locally
1. Download and install [Local server](https://www.mongodb.com/try/download/community)
2. Install [mongodb atlas compass](https://www.mongodb.com/try/download/compass) for GUI based portal.
3. Configure the search index

## Installation

Use the project build app - maven to download, manage dependencies and run application.

For clean download and build
```bash
./mvnw clean install
```

To run the application locally:
```bash
./mvnw spring-boot:run
```

To deploy the application by creating a jar file (package):
```bash
./mvnw clean package
java -jar target/your-application-name-version.jar
```


## APIs
### Base URL:
`http://localhost:8080/`

### Endpoints
1. `/load-github-repos`
    Loads required repository data from GithubAPI Endpoint `https://api.github.com/orgs/brown-ccv/repos` to Database.
2. `/fetch-repos/filter`
    Fetches data from Database to client.

## Credits
- [MongoDB Atlas](https://www.mongodb.com/atlas) for database hosting.
- GitHub API for providing repository data.

## Troubleshooting Tips

- **Build Errors**: 
  Run `mvn clean` before packaging to clear any stale files.
- **JDK Compatibility**:
  Ensure the JDK version used is the same or greater than the required version. Use `mvn --version` to check.
- **MongoDB Connection Errors**:
  - Verify your MongoDB URI credentials and network permissions.
  - Ensure the MongoDB server is running.
- **Port Conflicts**:
  If port `8080` is occupied, specify a different port by adding the following in `application.properties`:
  ```properties
  server.port=<NEW_PORT>

## License

[MIT](https://choosealicense.com/licenses/mit/)