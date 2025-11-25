# Reversi-Othello COMP1009

A Java implementation of the Reversi/Othello board game using the **Model–View–Controller (MVC)** pattern.  
This project was completed as part of the **COMP1009 Programming Paradigms** coursework.  
The coursework required implementing:
- A **custom GUI** (`GUIView.java`)
- A **game logic controller** (`ReversiController.java`)
- A **board-square component** (`BoardSquareButton.java`)
- Strict separation of Model, View, and Controller
- Compliance with the provided interfaces and testing environment

Only the files written by me are provided in this repository.  

---

## 🎮 Features

- Two separate GUI windows:
  - One for the white player’s perspective
  - One for the black player’s perspective (rotated 180°)
- Fully drawn Othello pieces using Java Swing (no images allowed)
- Displays current player turn, invalid move feedback, and win/draw messages
- Valid move detection and full capture logic (horizontal, vertical, diagonal)
- Greedy AI that chooses the move capturing the highest number of pieces
- Restart functionality that resets the board to the initial 4-piece state
- Works with the **provided model**, as required by the coursework rules

---

## 📁 File Overview

| File | Description |
|------|-------------|
| `GUIView.java` | Custom Swing GUI implementing `IView` |
| `BoardSquareButton.java` | Custom component for displaying and interacting with board squares |
| `ReversiController.java` | Full game-logic controller implementing `IController` |

All other files (models, test files, views provided by the lecturer) are intentionally **excluded**.  

---

## ▶️ Running the Project

1. Open the project in **IntelliJ**, **Eclipse**, or any Java IDE.
2. Add the official starter files (from the module zip) into the project **outside of this repository**.
3. Add these files into the same `reversi` package:
