# Pac-Man OOP Project

![pacman-ss](https://github.com/user-attachments/assets/9f16553b-9092-4894-b740-b8903ed24fa9)

## Changelog
- [Changelog #1](Changelog%20%231.md) - Input Responsiveness Fix
- [Changelog #2](Changelog%20%232.md) - Increased Map Difficulty
- [Changelog #3](Changelog%20%233.md) - OOP Refactoring

---

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture Diagram](#architecture-diagram)
3. [File Structure](#file-structure)
4. [OOP Principles Explained](#oop-principles-explained)
5. [Code Flow](#code-flow)
6. [Team Presentation Guide](#team-presentation-guide)
7. [Common Questions & Answers](#common-questions--answers)
8. [Quick Reference Tables](#quick-reference-tables)

---

## Project Overview

This is a **Pac-Man game** built using Java Swing that demonstrates all four core OOP principles:
- **Encapsulation** - Private fields with getters/setters
- **Inheritance** - Class hierarchy (GameEntity → Player, Ghost, Wall, Food)
- **Polymorphism** - Method overriding and interface implementation
- **Abstraction** - Abstract classes and interfaces

Additionally, it includes:
- **Exception Handling** - Custom GameException class with try-catch blocks
- **File Handling** - Save/load high scores using FileWriter/BufferedReader

---

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                           App.java                                  │
│                    (Main entry point + Menu)                        │
│                                                                     │
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────┐               │
│  │ JFrame      │  │ JMenuBar     │  │ PacMan Panel │               │
│  │ (Window)    │  │ (File, Help) │  │ (Game)       │               │
│  └─────────────┘  └──────────────┘  └──────────────┘               │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│                         PacMan.java                                 │
│                    (Main Game Logic Panel)                          │
│                                                                     │
│  Uses: Player, Ghost, Wall, Food, ScoreManager, GameException       │
└─────────────────────────────────────────────────────────────────────┘
                              │
          ┌───────────────────┼───────────────────┐
          ▼                   ▼                   ▼
┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐
│   interfaces/   │  │     model/      │  │      util/      │
│                 │  │                 │  │                 │
│ • Collidable    │  │ • GameEntity    │  │ • ScoreManager  │
│ • Movable       │  │   (abstract)    │  │   (file I/O)    │
│                 │  │ • Player        │  │                 │
│                 │  │ • Ghost         │  │                 │
│                 │  │ • Wall          │  └─────────────────┘
│                 │  │ • Food          │
└─────────────────┘  └─────────────────┘
                              │
                              ▼
                    ┌─────────────────┐
                    │   exceptions/   │
                    │                 │
                    │ • GameException │
                    └─────────────────┘
```

---

## File Structure

```
pacman-java-master/
│
├── App.java                 # Entry point, creates window and menu
├── PacMan.java              # Main game panel with game logic
│
├── model/                   # Game entity classes
│   ├── GameEntity.java      # ABSTRACT base class
│   ├── Player.java          # Pac-Man (extends GameEntity)
│   ├── Ghost.java           # Enemy ghosts (extends GameEntity)
│   ├── Wall.java            # Maze walls (extends GameEntity)
│   └── Food.java            # Pellets (extends GameEntity)
│
├── interfaces/              # Interface definitions
│   ├── Collidable.java      # Collision detection contract
│   └── Movable.java         # Movement contract
│
├── exceptions/              # Custom exceptions
│   └── GameException.java   # Game-specific errors
│
├── util/                    # Utility classes
│   └── ScoreManager.java    # File save/load for scores
│
├── highscores.txt           # Sample data file
│
└── *.png                    # Game images
```

---

## OOP Principles Explained

### 1. ENCAPSULATION

**What it is:** Hiding internal data and providing controlled access through methods.

**Where in our code:**

| File | Line | Code Example |
|------|------|--------------|
| `model/GameEntity.java` | 11-17 | `private int x, y, width, height;` |
| `model/GameEntity.java` | 28-33 | `public int getX() { return x; }` |
| `model/GameEntity.java` | 35-38 | `public void setX(int x) { this.x = x; }` |
| `model/Player.java` | 11-15 | `private char direction; private int lives;` |
| `model/Ghost.java` | 11-17 | `private String color; private boolean isScared;` |

**How to explain:**
> "We use **private** fields so that other classes cannot directly modify our data. For example, in `GameEntity.java`, the position `x` and `y` are private. If another class wants to know where an entity is, it must call `getX()` or `getY()`. This protects our data from being accidentally changed to invalid values."

**Key points:**
- All fields in model classes are `private`
- Access is through public `getX()`, `setX()` methods
- Prevents external code from breaking internal state

---

### 2. INHERITANCE

**What it is:** Creating new classes based on existing classes to reuse code.

**Class Hierarchy:**
```
         GameEntity (abstract)
              │
    ┌─────────┼─────────┬─────────┐
    ▼         ▼         ▼         ▼
  Player    Ghost     Wall      Food
```

**Where in our code:**

| File | Line | Code Example |
|------|------|--------------|
| `model/GameEntity.java` | 10 | `public abstract class GameEntity` |
| `model/Player.java` | 8 | `public class Player extends GameEntity` |
| `model/Ghost.java` | 8 | `public class Ghost extends GameEntity` |
| `model/Wall.java` | 8 | `public class Wall extends GameEntity` |
| `model/Food.java` | 8 | `public class Food extends GameEntity` |

**How to explain:**
> "Instead of writing the same code four times for Player, Ghost, Wall, and Food, we created a parent class called `GameEntity`. All four classes **extend** GameEntity, so they automatically inherit common properties like position (x, y), size (width, height), and methods like `draw()` and `reset()`. This is called **inheritance** - child classes get everything from their parent."

**Benefits demonstrated:**
- `draw()` method inherited by all entities
- `reset()` method inherited and can be overridden
- Position/size fields shared across all entities

---

### 3. POLYMORPHISM

**What it is:** Same method name behaves differently based on which class calls it.

**Two types in our code:**

#### A) Method Overriding (Runtime Polymorphism)

| File | Line | Method | Behavior |
|------|------|--------|----------|
| `model/Player.java` | 73-75 | `update()` | Updates player image based on direction |
| `model/Ghost.java` | 70-71 | `update()` | Handles ghost AI logic |
| `model/Wall.java` | 14-15 | `update()` | Does nothing (walls are static) |
| `model/Food.java` | 36-37 | `update()` | Does nothing (food is static) |

**How to explain:**
> "The abstract class `GameEntity` declares an `update()` method that all children must implement. But each child implements it **differently**. When we call `entity.update()`, Java figures out at runtime which version to call - this is called **dynamic method dispatch** or runtime polymorphism."

#### B) Interface Polymorphism

| Interface | Implementing Classes |
|-----------|---------------------|
| `Collidable` | GameEntity (and all subclasses) |
| `Movable` | Player, Ghost |

```java
// In PacMan.java - treating different types uniformly
for (Ghost ghost : ghosts) {
    ghost.move();  // Movable interface method
}
pacman.move();     // Same method, different implementation
```

---

### 4. ABSTRACTION

**What it is:** Hiding complex implementation details, showing only what's necessary.

**Where in our code:**

#### Abstract Class:

| File | Line | Code |
|------|------|------|
| `model/GameEntity.java` | 10 | `public abstract class GameEntity` |
| `model/GameEntity.java` | 40 | `public abstract void update();` |

#### Interfaces:

| File | Purpose |
|------|---------|
| `interfaces/Collidable.java` | Defines collision detection contract |
| `interfaces/Movable.java` | Defines movement contract |

**How to explain:**
> "We use **abstract classes** and **interfaces** to define what something CAN DO without specifying HOW. The `GameEntity` class is abstract - you cannot create a `new GameEntity()`. You must create a `Player`, `Ghost`, `Wall`, or `Food`. The `Movable` interface says 'this thing can move' but doesn't say how - that's up to each class to decide."

---

### 5. EXCEPTION HANDLING

**What it is:** Gracefully handling errors instead of crashing.

**Where in our code:**

| File | Line | What it handles |
|------|------|-----------------|
| `exceptions/GameException.java` | entire file | Custom exception class |
| `util/ScoreManager.java` | 53-66 | File write errors |
| `util/ScoreManager.java` | 68-88 | File read errors |
| `PacMan.java` | 68-71 | Score loading errors |

**Custom Exception Example:**
```java
// GameException.java - our custom exception
public class GameException extends Exception {
    private int errorCode;
    
    public GameException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
```

**Try-Catch Example:**
```java
// ScoreManager.java - handling file errors
try {
    writer = new PrintWriter(new FileWriter(filename));
    // ... write scores ...
} catch (IOException e) {
    throw new GameException("Failed to save scores", GameException.FILE_ERROR, e);
}
```

---

### 6. FILE HANDLING

**What it is:** Reading and writing data to/from files.

**Where in our code:**

| File | Lines | Classes Used | Purpose |
|------|-------|--------------|---------|
| `util/ScoreManager.java` | 53-66 | `FileWriter`, `PrintWriter` | Saving scores |
| `util/ScoreManager.java` | 68-88 | `FileReader`, `BufferedReader` | Loading scores |

**File Format (highscores.txt):**
```
Champion,5000
ProGamer,4500
PacManMaster,4000
```

**How to explain:**
> "We save high scores to a text file using `FileWriter` and `PrintWriter`. When the game starts, we load existing scores using `FileReader` and `BufferedReader`. Each line in the file has format `name,score`. We wrap file operations in try-catch blocks to handle errors like missing files or permission problems."

---

## Code Flow

### Game Startup Flow
```
1. App.main() runs
   │
   ├──> Creates JFrame (window)
   ├──> Creates JMenuBar (File menu)
   ├──> Creates PacMan panel
   │       │
   │       ├──> Loads images
   │       ├──> Loads existing high scores (from file)
   │       ├──> loadMap() - creates all entities
   │       └──> Starts game loop timer (50ms = 20fps)
   │
   └──> Shows window
```

### Game Loop Flow (every 50ms)
```
actionPerformed() triggered by Timer
   │
   └──> move()
        │
        ├──> Move Pac-Man based on arrow keys
        │    └──> Check wall collision
        │
        ├──> For each Ghost:
        │    ├──> Check collision with Pac-Man
        │    ├──> Move ghost
        │    └──> Check wall collision → random direction
        │
        ├──> Check food collision → add score
        │
        └──> If all food eaten → load new level
   │
   └──> repaint() → calls draw()
```

### Collision Detection
```
collidesWith(other) in GameEntity:
   │
   └──> AABB (Axis-Aligned Bounding Box) check:
        
        ┌───────┐
        │   A   │  Collision if:
        │  ┌────┼───┐  - A.left < B.right
        └──┼────┘   │  - A.right > B.left
           │   B    │  - A.top < B.bottom
           └────────┘  - A.bottom > B.top
```

---

## Team Presentation Guide

### Person 1: Inheritance & Abstraction

**Files to focus on:**
- `model/GameEntity.java`
- `model/Player.java`
- `model/Ghost.java`

**Key talking points:**
1. "GameEntity is our abstract base class"
2. "It has abstract method update()"
3. "Player extends GameEntity and implements update()"
4. "Each subclass provides its own implementation of update()"

**Demo:**
- Open `GameEntity.java`, show `abstract` keyword
- Open `Player.java`, show `extends GameEntity`
- Explain how Player inherits x, y, width, height, draw(), etc.

---

### Person 2: Interfaces & Polymorphism

**Files to focus on:**
- `interfaces/Movable.java`
- `interfaces/Collidable.java`
- `PacMan.java`

**Key talking points:**
1. "Movable interface defines move() and updateDirection() - see Movable.java"
2. "Player and Ghost both implement Movable but move differently"
3. "In PacMan.java, we call ghost.move() and pacman.move() - same method name, different behavior"
4. "This is polymorphism - one interface, multiple implementations"

**Demo:**
- Show `Movable.java` interface
- Show `Player.java`: `public void move()`
- Show `Ghost.java`: `public void move()`
- Explain they do the same thing but could be different

---

### Person 3: Exception & File Handling

**Files to focus on:**
- `exceptions/GameException.java`
- `util/ScoreManager.java`
- `highscores.txt`

**Key talking points:**
1. "GameException is our custom exception class"
2. "ScoreManager handles saving and loading scores"
3. "We use FileWriter to save"
4. "We use BufferedReader to load"
5. "try-catch blocks handle errors gracefully"

**Demo:**
- Show `highscores.txt` file
- Run game, get a score, save it
- Show File → High Scores menu
- Show the file updated

---

## Common Questions & Answers

### Q1: "What is encapsulation and where is it in your code?"
> "Encapsulation means hiding data using private fields and providing getters/setters. In `GameEntity.java`, all fields like x, y, width, height are private. We provide getX(), setX() methods. This prevents other classes from setting invalid values directly."

### Q2: "Explain your inheritance hierarchy."
> "GameEntity is our abstract parent class. Player, Ghost, Wall, and Food all extend it. They inherit common properties like position and size, plus methods like draw() and reset(). This avoids code duplication."

### Q3: "Where is polymorphism demonstrated?"
> "In two places: First, the update() method - GameEntity declares it abstract, and each subclass implements it differently (Player updates its image, Ghost handles AI, Wall does nothing). Second, the Movable interface - both Player and Ghost implement move() but could have different movement logic."

### Q4: "What's the difference between abstract class and interface?"
> "Abstract class can have some implemented methods and fields (like GameEntity has draw() implemented). Interface only has method signatures with no implementation (like Movable just declares move() exists). A class can implement multiple interfaces but extend only one abstract class."

### Q5: "How does your file handling work?"
> "ScoreManager.java handles it. We use FileWriter and PrintWriter to save scores. We use FileReader and BufferedReader to load. Format is name,score per line. All file operations are wrapped in try-catch to handle IOExceptions."

### Q6: "What is your custom exception?"
> "GameException in exceptions/GameException.java. It extends Exception. It has error codes for different error types (FILE_ERROR, INVALID_STATE). We throw it when file operations fail and catch it in PacMan.java to show user-friendly messages."

### Q7: "How does collision detection work?"
> "We use AABB (Axis-Aligned Bounding Box) collision. In GameEntity.java, collidesWith() checks if two rectangles overlap by comparing their edges. If they overlap on both axes, there's a collision."

### Q8: "Walk me through the game loop."
> "Timer fires every 50ms (20fps). actionPerformed() calls move() which: 1) Moves Pac-Man based on stored direction, 2) Checks wall collisions, 3) Moves each ghost and handles their wall collisions, 4) Checks food collision and adds score, 5) Checks ghost collision (lose life). Then repaint() redraws everything."

---

## Quick Reference Tables

### Classes and Their Purpose

| Class | Package | Purpose | OOP Concepts |
|-------|---------|---------|--------------|
| `App` | (root) | Entry point, creates window/menu | GUI |
| `PacMan` | (root) | Game panel, game loop | GUI, Polymorphism |
| `GameEntity` | model | Abstract base class | Abstraction, Encapsulation |
| `Player` | model | Pac-Man character | Inheritance, Polymorphism |
| `Ghost` | model | Enemy ghosts | Inheritance, Polymorphism |
| `Wall` | model | Maze walls | Inheritance |
| `Food` | model | Pellets to eat | Inheritance |
| `Collidable` | interfaces | Collision contract | Abstraction |
| `Movable` | interfaces | Movement contract | Abstraction, Polymorphism |
| `GameException` | exceptions | Custom errors | Exception Handling |
| `ScoreManager` | util | File I/O for scores | File Handling |

### Required vs Implemented

| Requirement | Status | Where |
|-------------|--------|-------|
| Java Swing GUI | ✅ | App.java, PacMan.java |
| Menu-driven interface | ✅ | App.java (JMenuBar) |
| Encapsulation | ✅ | All model classes (private + getters/setters) |
| Inheritance | ✅ | GameEntity → Player, Ghost, Wall, Food |
| Polymorphism | ✅ | update() override, Movable interface |
| Abstraction | ✅ | GameEntity (abstract), interfaces |
| Exception Handling | ✅ | GameException + try-catch in ScoreManager |
| Custom Exception | ✅ | exceptions/GameException.java |
| File Handling | ✅ | util/ScoreManager.java + highscores.txt |

### Keyboard Shortcuts (for demo)

| Action | Shortcut |
|--------|----------|
| New Game | Ctrl+N |
| Save Score | Ctrl+S |
| High Scores | Ctrl+H |
| Exit | Ctrl+Q |
| Move Up | Arrow Up |
| Move Down | Arrow Down |
| Move Left | Arrow Left |
| Move Right | Arrow Right |

---

## How to Compile and Run

```bash
# Compile all Java files
javac -d . model/*.java interfaces/*.java exceptions/*.java util/*.java *.java

# Run the game
java App
```

Or if using an IDE:
1. Open the project folder
2. Run `App.java`

---

## Summary for Instructor

This Pac-Man project demonstrates:

1. **GUI**: Java Swing with JFrame, JPanel, JMenuBar
2. **Encapsulation**: Private fields in all model classes with public getters/setters
3. **Inheritance**: GameEntity (abstract) → Player, Ghost, Wall, Food
4. **Polymorphism**: 
   - Method overriding (update() in subclasses)
   - Interface implementation (Movable, Collidable)
5. **Abstraction**: Abstract GameEntity class + Movable/Collidable interfaces
6. **Exception Handling**: Custom GameException + try-catch blocks
7. **File Handling**: FileWriter/PrintWriter and FileReader/BufferedReader in ScoreManager

All four core OOP principles are demonstrated with practical, working code in a functional game application.
