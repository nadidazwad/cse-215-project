package exceptions;

/**
 * Custom exception for game errors.
 */
public class GameException extends Exception {
    
    public static final int FILE_ERROR = 1;
    public static final int INVALID_STATE = 2;
    public static final int RESOURCE_ERROR = 3;
    
    private int errorCode;
    
    public GameException(String message) {
        super(message);
        this.errorCode = 0;
    }
    
    public GameException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public GameException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 0;
    }
    
    public GameException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
    
    public String getUserFriendlyMessage() {
        switch (errorCode) {
            case FILE_ERROR: return "Unable to read or write game files.";
            case INVALID_STATE: return "Invalid game state. Please restart.";
            case RESOURCE_ERROR: return "Failed to load game resources.";
            default: return getMessage();
        }
    }
}
