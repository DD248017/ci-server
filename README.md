# 🚀 DD2480 Assignment 2 Overview

This project involves developing a lightweight Continuous Integration (CI) server that automatically compiles, tests, and reports build results for a given repository. The CI server listens for webhook triggers from GitHub, pulls the latest code changes, and runs `mvn compile` and `mvn test` to ensure code integrity. It then notifies users of the build results through commit status updates or email notifications. The project also requires proper repository structuring, clear documentation, and adherence to software engineering best practices, including well-structured commits and collaboration. Additionally, advanced features like build history tracking and enhanced automation can be implemented for distinction.

## 🛠️ Core Components:

- **Compilation**:

  - The CI server support compiling the project when triggered by a webhook.
  - It fetch the latest code from the specified branch and run mvn compile.

- **Automated Testing**:

  - The CI server run mvn test upon receiving a webhook event.
  - The Ci server recives the specific branch in the webhook payload where the change was made.
  - The CI server detects changes and execute the appropriate tests.

- **CI Result Notification**:

  - The CI server notifies users about build results through commit status updates via the GitHub REST API.

- **Version Control and Traceability**:

  - Each CI run is associated with a specific Git commit.
  - The CI server maintains a history of past builds, with each build having a unique URL for logs.

- **Documentation**:

  - All public classes and methods have API documentation (e.g., JavaDoc).
  - A comprehensive README, documenting：
    - How to set up and run the CI server
    - Dependencies and required configurations
    - Contributions of team members
    - How the CI server is implemented and tested

- **Webhook Configuration**:

  - Configure GitHub to trigger the CI server by setting the webhook URL to point to your CI server's public URL form ngrok.

- **URLS**:

```console
/webhook              for webook listener
```

```console
/history              history of the past builds
```

```console
/docs                 for documentation
```

```console
/health               for server health check
```

## ⚙️ Running the Project with Maven

### Prerequisites

Before running the project, ensure you have Maven and Java installed. The required versions are:

- **Maven version:** apache-maven-3.9.9
- **Java version:** 17.0.2

If you haven’t installed Maven yet, follow the instructions [here](https://maven.apache.org/install.html).

Then you need to generate a personal github token [here]https://github.com/settings/personal-access-tokens

After that add it as a system enviroment varible called GITHUB_TOKEN.

### Build the Project

To compile and package the project, run:

```console
mvn clean install
```

### Run the Application

Execute the following command to start the application:

```console
mvn exec:java
```

### Running Tests

#### Run All Tests

To execute all test cases, use:

```console
mvn test
```

#### Run single test

To run a specific test class, for example CompileServiceTest use:

```console
mvn test -Dtest=CompileServiceTest
```

## Test Builds Folder

The testBuilds.zip file contains builds used for testing core functions.

## 🤝 Statement of contributions

Everyone has been active in this group project, and the collaboration has been excellent. From the beginning, we discussed each other's skills and divided the work accordingly, ensuring that everyone could contribute in ways that played to their strengths.

### Contributors:

- Giacomo Ricco
- Kohei Kuramoto
- Max Linghag Ahlgren
- Simon Li
- Yuhang Lin

### Specific contributions to the project:

```console
## 🤝 Statement of Contributions

Everyone has been active in this group project, and the collaboration has been excellent. From the beginning, we discussed each other's skills and divided the work accordingly, ensuring that everyone could contribute in ways that played to their strengths.

### Contributors and Their Contributions:

Before running tests that depend on build logs, unzip the /testBuilds folder. This folder contains the necessary log files required for testing.

### Contributors and Their Contributions:

- **Kohei Kuramoto**:
  - Implemented unit tests for GitService.
  - Implemented unit tests for Handlers.
  - Added comprehensive Javadoc to /services functions and classes.

- **Max Linghag Ahlgren**:
  - Added handlers to process webhook events and trigger CI workflows.
  - Implemented core logic for processing incoming Git webhook events.

- **Simon Li (ssimli)**:
  - Developed and merged tests for the notification service.
  - Contributed to overall testing improvements.

- **Yuhang Lin**:
  - Updated the webhook log files to ensure accurate logging of webhook events.
  - Designed a modular and scalable architecture.
  - Converted javadocs into html format.

- **Giacomo Ricco**:
  - Coordinated the team and contributed to project documentation and overall design.
  - Fixed a detailed report covering performance, collaboration and error analysis.

### Additional Documentation:
- "Way of working" docs – Giacomo Ricco

```

## 📜 Javadoc & API Documentation

Comprehensive Javadoc comments are provided in the source code.
To generate the API documentation in HTML format, run:

```console
mvn javadoc:javadoc
```

## 📜 License

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License). You are free to use, modify, and distribute this project for personal or commercial purposes.

Conditions:

- You may use and test the code freely.
- You must include the original license and copyright notice in all copies or substantial portions of the software.
- This software is provided "as is", without warranty of any kind.
