# 🎮 Smart Tic-Tac-Toe

A modern, beautifully designed Tic-Tac-Toe game built using **Java Swing**, featuring both **Player vs Player** and **Player vs AI (Minimax)** modes.
This project was created as part of a team assignment to demonstrate UI design, event-driven programming, and game logic implementation in Java.

---

## 🚀 Features

### ✔️ Modern GUI

* Custom color palette
* Hover animations
* Clean typography (Segoe UI)
* Card-based navigation (Home ↔ Game)

### ✔️ Gameplay Modes

* **Player vs Player (PvP)**
* **Player vs AI (Minimax Algorithm)**

### ✔️ Smart AI

* AI uses **Minimax algorithm** with depth evaluation
* Always plays optimally
* Impossible to beat (unless you draw 😄)

### ✔️ Score Tracking

* Tracks **X Wins**, **O Wins**, **Draws**
* Reset without losing scoreboard

### ✔️ Other Features

* Smooth hover effects
* Reset button
* Return to Home menu
* Error-free user interactions
* Automatically detects wins, losses, draws

---

## 🖼️ Screenshots

(Replace these with actual images later.)

### 🏠 Home Screen

<img width="1919" height="1019" alt="image" src="https://github.com/user-attachments/assets/4a4c61b0-30fb-4393-b1f7-6abfe66a47cb" />


### 🎯 Game Screen

<img width="1919" height="1018" alt="image" src="https://github.com/user-attachments/assets/9635383f-d5e1-4ff7-b57e-991ada28cead" />


---

## 📁 Project Structure

```
📦 Smart-Tic-Tac-Toe
 ┣ 📜 TicTacToe.java
 ┗ 📜 README.md
```

---

## 🧠 How the AI Works

The AI uses a **Minimax algorithm**, a classical game-tree search technique.

### 🔍 Steps:

1. Simulates all possible moves
2. Evaluates each using:

   * +10 for AI win
   * -10 for player win
   * 0 for draw
3. Picks the move with the highest score
4. Always optimal, never plays random

This makes the AI almost impossible to defeat.

---

## 🖥️ How to Run the Project

### **Requirements**

* Java JDK 8+
* Any IDE (IntelliJ, Eclipse, VS Code, NetBeans)

### **Run Using Terminal**

```sh
javac TicTacToe.java
java TicTacToe
```

### **Run Using IDE**

* Open project
* Compile `TicTacToe.java`
* Run the main method

---

## 🎮 Game Controls

| Action       | Description                |
| ------------ | -------------------------- |
| Click a tile | Place X or O               |
| Reset Game   | Clear board but keep score |
| Back to Menu | Return to home screen      |
| Exit         | Quit application           |

---

## 📌 Future Improvements

* Add difficulty levels (Easy/Medium/Hard)
* Add animated win-line detection
* Add sound effects
* Add 5×5 or 4×4 extended mode

---

## ⭐ If you like this project…

Consider giving the repository a **Star ⭐** on GitHub!

---
