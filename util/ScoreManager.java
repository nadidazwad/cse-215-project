package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import exceptions.GameException;

/**
 * Handles saving and loading high scores.
 */
public class ScoreManager {
    
    private String filename;
    private ArrayList<ScoreEntry> highScores;
    private static final int MAX_SCORES = 10;
    
    public static class ScoreEntry {
        private String playerName;
        private int score;
        
        public ScoreEntry(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
        
        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }
    }
    
    public ScoreManager() {
        this.filename = "highscores.txt";
        this.highScores = new ArrayList<>();
    }
    
    public ScoreManager(String filename) {
        this.filename = filename;
        this.highScores = new ArrayList<>();
    }
    
    public void saveScore(String playerName, int score) throws GameException {
        highScores.add(new ScoreEntry(playerName, score));
        
        Collections.sort(highScores, new Comparator<ScoreEntry>() {
            @Override
            public int compare(ScoreEntry a, ScoreEntry b) {
                return b.getScore() - a.getScore();
            }
        });
        
        while (highScores.size() > MAX_SCORES) {
            highScores.remove(highScores.size() - 1);
        }
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(filename));
            for (ScoreEntry entry : highScores) {
                writer.println(entry.getPlayerName() + "," + entry.getScore());
            }
        } catch (IOException e) {
            throw new GameException("Failed to save scores", GameException.FILE_ERROR, e);
        } finally {
            if (writer != null) writer.close();
        }
    }
    
    public void loadScores() throws GameException {
        highScores.clear();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    highScores.add(new ScoreEntry(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                }
            }
        } catch (IOException e) {
            throw new GameException("Failed to load scores", GameException.FILE_ERROR, e);
        } catch (NumberFormatException e) {
            throw new GameException("Invalid score format", GameException.FILE_ERROR, e);
        } finally {
            if (reader != null) {
                try { reader.close(); } catch (IOException e) {}
            }
        }
    }
    
    public ArrayList<ScoreEntry> getHighScores() {
        return new ArrayList<>(highScores);
    }
    
    public boolean isHighScore(int score) {
        if (highScores.size() < MAX_SCORES) return true;
        return score > highScores.get(highScores.size() - 1).getScore();
    }
    
    public int getTopScore() {
        return highScores.isEmpty() ? 0 : highScores.get(0).getScore();
    }
    
    public void clearScores() throws GameException {
        highScores.clear();
        try {
            new PrintWriter(new FileWriter(filename)).close();
        } catch (IOException e) {
            throw new GameException("Failed to clear scores", GameException.FILE_ERROR, e);
        }
    }
    
    public String getFormattedScores() {
        if (highScores.isEmpty()) return "No high scores yet!";
        
        StringBuilder sb = new StringBuilder("=== HIGH SCORES ===\n");
        for (int i = 0; i < highScores.size(); i++) {
            ScoreEntry entry = highScores.get(i);
            sb.append(String.format("%2d. %-15s %6d\n", i + 1, entry.getPlayerName(), entry.getScore()));
        }
        return sb.toString();
    }
}
