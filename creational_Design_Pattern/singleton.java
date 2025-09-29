package creational_Design_Pattern;

class Logger {
    private static Logger instance;
    private Logger() {}
    public static Logger getInstance() {
        if (instance == null) instance = new Logger();
        return instance;
    }
    public void log(String msg) { System.out.println("[LOG]: " + msg); }
}

public class singleton {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        logger.log("Application started");
        Logger another = Logger.getInstance();
        another.log("Same instance reused");
    }
}
