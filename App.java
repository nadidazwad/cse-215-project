import javax.swing.*;
import java.awt.event.*;

/**
 * Main application entry point.
 */
public class App {
    
    private static PacMan pacmanGame;
    
    public static void main(String[] args) {
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int boardWidth = columnCount * tileSize;
        int boardHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Pac Man");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameItem.addActionListener(e -> pacmanGame.newGame());
        
        JMenuItem saveScoreItem = new JMenuItem("Save Score");
        saveScoreItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveScoreItem.addActionListener(e -> { pacmanGame.saveCurrentScore(); pacmanGame.requestFocus(); });
        
        JMenuItem highScoresItem = new JMenuItem("High Scores");
        highScoresItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        highScoresItem.addActionListener(e -> { pacmanGame.showHighScores(); pacmanGame.requestFocus(); });
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frame, "Exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            pacmanGame.requestFocus();
        });
        
        fileMenu.add(newGameItem);
        fileMenu.addSeparator();
        fileMenu.add(saveScoreItem);
        fileMenu.add(highScoresItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu helpMenu = new JMenu("Help");
        
        JMenuItem howToPlayItem = new JMenuItem("How to Play");
        howToPlayItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                "Arrow Keys - Move Pac-Man\nEat all dots, avoid ghosts.\nEach dot = 10 points",
                "How to Play", JOptionPane.INFORMATION_MESSAGE);
            pacmanGame.requestFocus();
        });
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Pac-Man OOP Project\nJava Swing", "About", JOptionPane.INFORMATION_MESSAGE);
            pacmanGame.requestFocus();
        });
        
        helpMenu.add(howToPlayItem);
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
