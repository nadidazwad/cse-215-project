import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import model.*;
import exceptions.GameException;
import util.ScoreManager;

/**
 * Main game panel for Pac-Man.
 */
public class PacMan extends JPanel implements ActionListener, KeyListener {

    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int boardWidth = columnCount * tileSize;
    private int boardHeight = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhostImage, orangeGhostImage, pinkGhostImage, redGhostImage, scaredGhostImage;
    private Image pacmanUpImage, pacmanDownImage, pacmanLeftImage, pacmanRightImage;

    // X = wall, O = skip, P = pac man, ' ' = food, b/o/p/r = ghosts
    private String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXXXX X   X XXXXXX",
        "OOOX X X   X X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X XXXXXXX X XX X",
        "X    X       X    X",
        "X XXXX XXXXX XXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    private ArrayList<Wall> walls;
    private ArrayList<Food> foods;
    private ArrayList<Ghost> ghosts;
    private Player pacman;

    private Timer gameLoop;
    private char[] directions = {'U', 'D', 'L', 'R'};
    private Random random = new Random();
    private int score = 0;
    private boolean gameOver = false;
    private char nextDirection = 'R';
    private ScoreManager scoreManager;

    public PacMan() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        scoreManager = new ScoreManager("highscores.txt");
        try {
            scoreManager.loadScores();
        } catch (GameException e) {
            System.out.println("Note: " + e.getUserFriendlyMessage());
        }

        loadImages();
        loadMap();
        
        for (Ghost ghost : ghosts) {
            ghost.updateDirection(directions[random.nextInt(4)]);
        }
        
        gameLoop = new Timer(50, this); // 20fps
        gameLoop.start();
    }
    
    private void loadImages() {
        wallImage = new ImageIcon(getClass().getResource("./wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./redGhost.png")).getImage();
        
        try {
            scaredGhostImage = new ImageIcon(getClass().getResource("./scaredGhost.png")).getImage();
        } catch (Exception e) {
            scaredGhostImage = blueGhostImage;
        }

        pacmanUpImage = new ImageIcon(getClass().getResource("./pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("./pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("./pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("./pacmanRight.png")).getImage();
    }

    public void loadMap() {
        walls = new ArrayList<>();
        foods = new ArrayList<>();
        ghosts = new ArrayList<>();

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < columnCount; c++) {
                char ch = tileMap[r].charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;

                if (ch == 'X') {
                    walls.add(new Wall(wallImage, x, y, tileSize, tileSize));
                } else if (ch == 'b') {
                    Ghost ghost = new Ghost(blueGhostImage, x, y, tileSize, tileSize, "blue", tileSize);
                    ghost.setScaredImage(scaredGhostImage);
                    ghosts.add(ghost);
                } else if (ch == 'o') {
                    Ghost ghost = new Ghost(orangeGhostImage, x, y, tileSize, tileSize, "orange", tileSize);
                    ghost.setScaredImage(scaredGhostImage);
                    ghosts.add(ghost);
                } else if (ch == 'p') {
                    Ghost ghost = new Ghost(pinkGhostImage, x, y, tileSize, tileSize, "pink", tileSize);
                    ghost.setScaredImage(scaredGhostImage);
                    ghosts.add(ghost);
                } else if (ch == 'r') {
                    Ghost ghost = new Ghost(redGhostImage, x, y, tileSize, tileSize, "red", tileSize);
                    ghost.setScaredImage(scaredGhostImage);
                    ghosts.add(ghost);
                } else if (ch == 'P') {
                    pacman = new Player(pacmanRightImage, x, y, tileSize, tileSize, tileSize);
                    pacman.setDirectionImages(pacmanUpImage, pacmanDownImage, pacmanLeftImage, pacmanRightImage);
                } else if (ch == ' ') {
                    foods.add(new Food(x + 14, y + 14, 4, 4));
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        pacman.draw(g);
        for (Ghost ghost : ghosts) ghost.draw(g);
        for (Wall wall : walls) wall.draw(g);
        for (Food food : foods) food.draw(g);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver) {
            g.drawString("Game Over: " + score, tileSize/2, tileSize/2);
            g.drawString("Press any key to restart", tileSize/2, tileSize);
        } else {
            g.drawString("x" + pacman.getLives() + " Score: " + score, tileSize/2, tileSize/2);
        }
    }

    public void move() {
        if (pacman.getDirection() != nextDirection) {
            char prevDirection = pacman.getDirection();
            pacman.updateDirection(nextDirection);
            pacman.move();
            
            for (Wall wall : walls) {
                if (pacman.collidesWith(wall)) {
                    pacman.undoMove();
                    pacman.updateDirection(prevDirection);
                    break;
                }
            }
        }

        pacman.move();
        for (Wall wall : walls) {
            if (pacman.collidesWith(wall)) {
                pacman.undoMove();
                break;
            }
        }

        for (Ghost ghost : ghosts) {
            if (ghost.collidesWith(pacman)) {
                if (!pacman.loseLife()) {
                    gameOver = true;
                    handleGameOver();
                    return;
                }
                resetPositions();
            }

            if (ghost.shouldEscapeSpawn(tileSize * 9)) {
                char prevDir = ghost.getDirection();
                ghost.updateDirection('U');
                ghost.move();
                
                boolean blocked = false;
                for (Wall wall : walls) {
                    if (ghost.collidesWith(wall)) {
                        blocked = true;
                        break;
                    }
                }
                
                if (blocked) {
                    ghost.undoMove();
                    ghost.updateDirection(prevDir);
                } else {
                    continue;
                }
            }
            
            ghost.move();
            
            for (Wall wall : walls) {
                if (ghost.collidesWith(wall) || ghost.getX() <= 0 || ghost.getX() + ghost.getWidth() >= boardWidth) {
                    ghost.undoMove();
                    ghost.updateDirection(directions[random.nextInt(4)]);
                }
            }
        }

        Food foodEaten = null;
        for (Food food : foods) {
            if (pacman.collidesWith(food) && !food.isEaten()) {
                foodEaten = food;
                score += food.eat();
            }
        }
        if (foodEaten != null) foods.remove(foodEaten);

        if (foods.isEmpty()) {
            loadMap();
            resetPositions();
        }
    }

    public void resetPositions() {
        pacman.reset();
        for (Ghost ghost : ghosts) {
            ghost.reset();
            ghost.updateDirection(directions[random.nextInt(4)]);
        }
    }
    
    private void handleGameOver() {
        try {
            if (scoreManager.isHighScore(score)) {
                String name = JOptionPane.showInputDialog(this, "High Score! Enter your name:", "New High Score", JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.trim().isEmpty()) {
                    scoreManager.saveScore(name.trim(), score);
                }
            }
        } catch (GameException e) {
            JOptionPane.showMessageDialog(this, e.getUserFriendlyMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showHighScores() {
        try {
            scoreManager.loadScores();
            JOptionPane.showMessageDialog(this, scoreManager.getFormattedScores(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
        } catch (GameException e) {
            JOptionPane.showMessageDialog(this, e.getUserFriendlyMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void saveCurrentScore() {
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "Save Score", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            try {
                scoreManager.saveScore(name.trim(), score);
                JOptionPane.showMessageDialog(this, "Score saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (GameException e) {
                JOptionPane.showMessageDialog(this, e.getUserFriendlyMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void newGame() {
        loadMap();
        resetPositions();
        pacman.setLives(3);
        score = 0;
        gameOver = false;
        nextDirection = 'R';
        if (!gameLoop.isRunning()) gameLoop.start();
        requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) gameLoop.stop();
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) newGame();
        
        if (e.getKeyCode() == KeyEvent.VK_UP) nextDirection = 'U';
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) nextDirection = 'D';
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) nextDirection = 'L';
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) nextDirection = 'R';
    }
    
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
}
