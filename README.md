# PeopleConnecter

PeopleConnecter is a Java-based application designed to match individuals structured as participants with specific roles and desired connections. It reads a list of participants, evaluates potential pairs based on a defined scoring algorithm, and determines the optimal networking connections for each person.

## Purpose

The primary goal of the project is to generate high-quality connections between professionals or individuals based on what they do (their occupations) and who they are looking for (desired occupations). The algorithm ensures that each participant gets a limited number of best possible matches (up to 5), maximizing the overall satisfaction (quality score) of the entire group.

## Architecture & Module Structure

The project is built using a clean, layered architecture with separation of concerns:

- **`MainApp.java`**: The entry point of the application. It orchestrates the flow: validating arguments, loading input, running the matching algorithm, and presenting the output.
- **`models`**: Data transfer objects represented as Java Records.
  - `Participant`: Holds ID, current occupations, and desired occupations.
  - `Pair`: Represents a connection between two participants along with their calculated match quality score.
- **`input`**: Handles reading and parsing input data.
  - `TxtFileInputParser`: Reads tab-separated text files (e.g., `people.txt`) and maps them to `Participant` objects.
  - `ArgumentsValidator`: Parses command-line arguments for custom input/output file paths.
- **`algorithm`**: The core business logic for generating connections.
  - `OptimizedConnectingAlgorithm`: Generates all possible pairs, filters them based on capacity constraints (max 5 connections per person), and optimizes the selections to maximize total matching quality.
  - `evaluator.DefaultMatchEvaluator`: Calculates the compatibility score between two participants based on common desired roles, unique traits, and perfect bidirectional matches.
  - `helpers`: Contains utility classes (`PairGenerator`, `PairOptimizer`, `ConnectionCounter`) that assist the main algorithm in sorting, limiting, and optimizing pairs.
- **`output`**: Manages the presentation of the results.
  - `ConsoleOutputPresenter`: Prints the connections and their quality scores to the console.
  - `TxtFileOutputPresenter`: Saves the results to a structured text file (e.g., `connections.txt`).

## Technologies & Libraries

- **Java 21**: Utilizes modern Java features such as Records, Streams API, and advanced Collections.
- **Maven**: Used for project dependency management and build configuration (`pom.xml`).
- *No external third-party libraries* (like Spring or Hibernate) are used; the application relies entirely on Core Java, keeping it lightweight and fast.

## Build and Run Instructions

### Prerequisites
- Java Development Kit (JDK) 21 or higher installed.
- Apache Maven installed.

### Building the Application
To compile the project and build an executable JAR file, open your terminal in the project's root directory and run:

```bash
mvn clean package
```

This will generate a `PeopleConnecter-1.0-SNAPSHOT.jar` file inside the `target/` directory.

### Running the Application
You can run the application using the generated JAR file.

**Run with default files** (reads `people.txt` from resources and outputs to `connections.txt` in the root directory):
```bash
java -jar target/PeopleConnecter-1.0-SNAPSHOT.jar
```

**Run with custom input and output files**:
You can optionally provide arguments to specify input and output files:
```bash
java -jar target/PeopleConnecter-1.0-SNAPSHOT.jar custom_people.txt custom_connections.txt
```
*(Note: The input file must be available in the classpath/resources as per the current parser implementation).*
