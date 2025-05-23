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

## 📁 File Handling (JSON & CSV)

### 🧩 Importing and Exporting Boards (JSON)

The game supports custom board configurations via JSON. To use this feature:

1. Select **`Importer eget brett (JSON)`** from the dropdown menu on the start screen:

   ![Select JSON board]()

2. Set the number of players **or** import from CSV (see next section).
3. Click **Neste** to proceed to the player setup screen.
4. Click **Neste** again to open a file dialog and select a valid JSON board file:

   ![Select JSON file](![image](https://github.com/user-attachments/assets/2b006a95-a166-4654-8455-f55cd5b4f36a)
)

5. If the JSON file structure is invalid, the system will not proceed. Use the included example (`src/main/resources/importfunctionreadymadeboards/`) as a template.

You can also save a board configuration from within the game using the **Lagre brett** button and saving the file where you prefer:
   ![Save board file](<img width="1272" alt="Skjermbilde 2025-05-23 kl  10 22 56" src="https://github.com/user-attachments/assets/f2cd207f-9240-4fef-9198-f254b7525c6a" />
)

The included boards (small, medium, large) support all required `TileAction` types and meet the dice specifications from the assignment.

---

### 👥 Importing and Exporting Players (CSV)

#### ✅ Import Players from CSV

To import players:

- Click **`Importer spillere inn fra en CSV-fil`**
- Ensure the CSV file follows the correct format:

  ```csv
  name,age,token
  Arne,22,Horse
  Ivar,25,Rook


### ✅ CSV Token Requirements

- Tokens must match one of the **5 predefined game tokens** available in the application.

📌 **Note:** If you import players from a CSV file, the number-of-players dropdown is **ignored**.

---

### 💾 Export Players to CSV

To export a player setup:

1. Select a board type from the dropdown.
2. Choose the number of players.
3. Fill in **name**, **age**, and select a **token** for each player.
4. Click **Lagre spillere til CSV-fil**.

🛑 **Warning:** The application does not block you from exporting malformed CSV files. Make sure:
- Each player has a **unique token**
- All fields follow the required format: `name,age,token`
As it is today, it is possible to export and import csv-files where players have the same tokens, but the game will not start unless you change the tokens. 

---

### 💡 Tip

All saved boards and player files can be placed **anywhere** on your local machine. The app uses standard system file dialogs for selecting files, making it flexible to load and save from any path.

---

## ⚠️ Limitations

- **Language**: The application interface is only available in Norwegian, which may limit accessibility for non-Norwegian speakers.
- **CSV Import**: There is only some validation when exporting player data to CSV. This means files may contain invalid data such as:
  - Duplicate tokens for different players
  These files will not load correctly on import.
- **Local-Only Gameplay**: As per the assignment constraints, the game is designed for local use on a single machine. There is no support for online or networked multiplayer.

### ⚠️ TileAction Limitation

The system supports `TileAction` logic but is limited to **one action per turn**.  
If a player lands on a special tile (e.g., `LadderAction`) that moves them to another tile with an action, **only the first action is executed**, any subsequent actions are ignored.

While recursive or iterative handling of chained actions could solve this, it was considered outside the scope of this project.

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
