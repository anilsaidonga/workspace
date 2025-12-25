package workspace;

enum LogLevel { DEBUG, INFO, ERROR }

interface ILogger {
    void log(String message);
}

class DebugLogger implements ILogger {
    @Override
    public void log(String message) {
        System.out.println("[DEBUG] " + message);
    }
}

class InfoLogger implements ILogger {
    @Override
    public void log(String message) {
        System.out.println("[INFO]  " + message);
    }
}

class ErrorLogger implements ILogger {
    @Override
    public void log(String message) {
        System.err.println("[ERROR] " + message);
    }
}

class LoggerFactory {
    public static ILogger createLogger(LogLevel level) {
        if (level == null) throw new IllegalArgumentException("LogLevel cannot be null");
        switch (level) {
            case DEBUG: return new DebugLogger();
            case INFO:  return new InfoLogger();
            case ERROR: return new ErrorLogger();
            default:    throw new IllegalArgumentException("Unsupported LogLevel: " + level);
        }
    }
}

public class LoggerDemo {
    public static void main(String[] args) {
        ILogger debugLogger = LoggerFactory.createLogger(LogLevel.DEBUG);
        debugLogger.log("This is a debug log message.");
    }
}
