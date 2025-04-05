package org.codingassignment;

import java.io.IOException;
import java.util.logging.*;

public class SmartHomeLogger {
    private static final Logger LOGGER = Logger.getLogger(SmartHomeLogger.class.getName());
    private static final String LOG_FILE = "smartHome.log";
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) {
            return;
        }

        try {
            // File handler for logging to a file
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setFormatter(new SimpleFormatter());

            // Remove default handlers to add custom ones
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();

            for (Handler handler : handlers) {
                rootLogger.removeHandler(handler);
            }

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);

            initialized = true;

            msg("Logging system initialized");
        } catch (IOException e) {
            System.err.println("Failed to initialize logging system: " + e.getMessage());
        }
    }

    public static void msg(String message) {
        LOGGER.info(message);
    }

    public static void warning(String message) {
        LOGGER.warning(message);
    }

    public static void error(String message) {
        LOGGER.severe(message);
    }
}
