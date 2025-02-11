Test webhook
Test webhook2
Test webhook3
Test webhook4
Test webhook5
Test webhook6
Test webhook7

# 🚀 DD2480 Assignment 2 Overview

This project involves developing a lightweight Continuous Integration (CI) server that automatically compiles, tests, and reports build results for a given repository. The CI server listens for webhook triggers from a Git platform like GitHub, pulls the latest code changes, and runs `mvn compile` and `mvn test` to ensure code integrity. It then notifies users of the build results through commit status updates or email notifications. The project also requires proper repository structuring, clear documentation, and adherence to software engineering best practices, including well-structured commits and collaboration. Additionally, advanced features like build history tracking and enhanced automation can be implemented for distinction.

## 🛠️ Core Components:

- **Compilation**: 
  - The CI server must support compiling the project when triggered by a webhook.
  - It should fetch the latest code from the specified branch and run mvn compile.
  - If using an interpreted language, it should perform a static syntax check.

- **Automated Testing**: 
  - The CI server must run mvn test upon receiving a webhook event.
  - The webhook payload should specify the branch where the change was made. 
  - The CI server should detect changes and execute the appropriate tests.

- **CI Result Notification**: 
  - The CI server must notify users about build results through at least one of the following:
    1. Commit status update (via GitHub REST API).
    2. Email notification to project members.

- **Version Control and Traceability**:
  - Each CI run must be associated with a specific Git commit.
  - The commit history must follow best practices。
  - The CI server should maintain a history of past builds, with each build having a unique URL for logs.

- **Documentation**:
  - All public classes and methods must have API documentation (e.g., JavaDoc).
  - A comprehensive README must be included, documenting：
    - How to set up and run the CI server
    - Dependencies and required configurations
    - Contributions of team members
    - How the CI server is implemented and tested

## 📂 Project Structure:

```console
// todo: Update project structure

/DD2480
├── /.github                  # CI/CD in GitHub
├── /src
│   ├── /main
│   │   └── /decide
│   │       ├── /application  # Main-function
│   │       ├── /core         # Core-functions
│   │       ├── /model        # Parameters etc
│   │       └── /util         # Help-functions
│   ├── /test
│   │   ├── /decide
|   |   |   ├── /decider      # Test entire program
|   |   |   └── /lic_judge    # LIC Tests
│   │   └── /resources        # Test input-files
├── /target                   # Generated class-files etc
├── /.gitignore
├── /pom.xml                  # mvn config
├── /docs                     # Project documentation
└── README.md                 # Project overview
```

## ⚙️ Running the Project with Maven

### Prerequisites

Before running the project, ensure you have Maven and Java installed. The required versions are:

- **Maven version:** apache-maven-3.9.9
- **Java version:** 17.0.2

If you haven’t installed Maven yet, follow the instructions [here](https://maven.apache.org/install.html).

### Build the Project

To compile and package the project, run:

```console
mvn clean install
```

### Run the Application

Execute the following command to start the application:

```console
mvn mvn exec:java -Dexec.mainClass=application.CiServer
```

### Running Tests

#### Run All Tests

To execute all test cases, use:

```console
mvn test
```

#### Run single test

To run a specific test class, for example LIC0 use:

```console
mvn test -Dtest=JsonParserTest
```

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
// todo: Add specific contributions here
- "Way of working" docs                # (XXX)
- README                               # (Lin & Max)
```

## 📜 License

This project is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License). You are free to use, modify, and distribute this project for personal or commercial purposes.

Conditions:

- You may use and test the code freely.
- You must include the original license and copyright notice in all copies or substantial portions of the software.
- This software is provided "as is", without warranty of any kind.
