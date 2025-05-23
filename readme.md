# 🧩 BoardGame Application

This is a Java-based board game application developed as part of the IDATT2003 course at NTNU. The system supports multiple board game types and is designed with modularity, reusability, and clean architecture in mind. The games implemented are **Snakes and Ladders** (LadderGame) and **Tic Tac Toe**.

## ✅ Features

- Select game variant from predefined or custom JSON board files
- Supports 2–5 players per game
- Unique player names and tokens (input validation included)
- Handles special tile actions (ladder up, fall down, skip turn, teleport, reset)
- Playable with 2 dice and automatic winner detection
- Import/export player data via CSV
- Save and load boards using JSON
- Graphical interface with JavaFX (no FXML)

## 🛠 Technologies Used

- **Java 21**
- **JavaFX** (GUI)
- **Apache Maven** (build & dependency management)
- **Gson** (JSON parsing)
- **JUnit 5** & **Mockito** (unit and integration testing)

## 🧱 Architecture

The project follows the **Model-View-Controller (MVC)** pattern:

- **Model**: Core domain classes (`Board`, `Tile`, `Player`, `Die`)
- **View**: JavaFX views like `StartScreenView`, `TicTacToeGameScreen`
- **Controller**: Controllers like `StartScreenController`, `MainPageController`, and `TicTacToeController` manage scene logic and input

### Design Patterns

- **Factory Pattern**: Used in `BoardGameFactory` to create game objects based on input
- **Observer Pattern**: Used to notify GUI of game state changes in real-time

## 🧪 Testing

The application includes:

- Unit tests for all model classes, `TileAction` implementations, and file handlers
- Integration tests simulating complete game flows
- GUI-independent tests using **Mockito** for mocking external dependencies

## 🚀 How to Run

1. Clone or download the repository
2. Open a terminal and navigate to the project root
3. Run the application with Maven:

```bash
mvn javafx:run