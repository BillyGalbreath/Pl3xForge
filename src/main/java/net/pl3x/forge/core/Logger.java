package net.pl3x.forge.core;

import org.apache.logging.log4j.Level;

import static org.apache.logging.log4j.Level.ERROR;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.WARN;

public class Logger {
    private static org.apache.logging.log4j.Logger logger;

    private Logger() {
    }

    public static void setLogger(org.apache.logging.log4j.Logger log) {
        logger = log;
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }

    public static void error(String message) {
        log(ERROR, message);
    }

    public static void info(String message) {
        log(INFO, message);
    }

    public static void warn(String message) {
        log(WARN, message);
    }
}
