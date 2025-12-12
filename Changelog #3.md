# Change #3: OOP Refactoring

## Problem
Original code didn't demonstrate proper OOP principles required for the project:
- No class hierarchy (inheritance)
- No encapsulation (public fields)
- No interfaces or abstract classes
- No custom exceptions
- No file handling

## Solution
Complete refactoring to implement all OOP requirements.

## Changes Made

### New Package Structure
```
model/          - Game entity classes
interfaces/     - Collidable, Movable interfaces
exceptions/     - GameException custom exception
util/           - ScoreManager for file I/O
```

### New Classes Created

| Class | Purpose |
|-------|---------|
| `GameEntity` | Abstract base class with encapsulation |
| `Player` | Pac-Man (extends GameEntity, implements Movable) |
| `Ghost` | Enemies (extends GameEntity, implements Movable) |
| `Wall` | Maze walls (extends GameEntity) |
| `Food` | Pellets (extends GameEntity) |
| `Collidable` | Interface for collision detection |
| `Movable` | Interface for movement |
| `GameException` | Custom exception for game errors |
| `ScoreManager` | File save/load for high scores |

### Menu Added
- File menu: New Game, Save Score, High Scores, Exit
- Help menu: How to Play, About

### File Handling
- `highscores.txt` stores player scores
- Uses FileWriter/PrintWriter for saving
- Uses FileReader/BufferedReader for loading

## Result
Project now demonstrates all required OOP principles:
- Encapsulation (private fields + getters/setters)
- Inheritance (GameEntity → subclasses)
- Polymorphism (method overriding + interfaces)
- Abstraction (abstract class + interfaces)
- Exception handling (custom GameException)
- File handling (ScoreManager)

