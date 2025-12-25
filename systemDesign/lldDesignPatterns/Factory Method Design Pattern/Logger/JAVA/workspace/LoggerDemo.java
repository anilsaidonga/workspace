
package workspace;

interface ILogger {
    void log(String msg);
}

class DebugLogger implements ILogger {
    @Override
    public void log(String msg) {
        System.out.println("DEBUG : " + msg);
    }   
}

class InfoLogger implements ILogger {
    @Override
    public void log(String msg) {
        System.out.println("INFO : " + msg);
    }
}   

class ErrorLogger implements ILogger {
    @Override
    public void log(String msg) {
        System.out.println("ERROR : " + msg);
    }   
}   

interface LoggerFactory {
    ILogger createLogger();
}   

class DebugLoggerFactory implements LoggerFactory {
    @Override
    public ILogger createLogger() {
        return new DebugLogger();
    }   
}   

class InfoLoggerFactory implements LoggerFactory {
    @Override
    public ILogger createLogger() {
        return new InfoLogger();
    }   
}   

class ErrorLoggerFactory implements LoggerFactory {
    @Override
    public ILogger createLogger() {
        return new ErrorLogger();
    }   
}   

public class LoggerDemo {
    public static void main(String[] args)
    {
        LoggerFactory loggerFactory = new InfoLoggerFactory();
        ILogger logger = loggerFactory.createLogger();
        logger.log("This is a info log message");
    }
}
