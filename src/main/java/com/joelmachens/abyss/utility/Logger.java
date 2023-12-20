package com.joelmachens.abyss.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger that outputs logs to console and file, timestamped and where relevant coloured.
 */
public class Logger {
    private static final DateTimeFormatter LOCAL_TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";

    private static void logToFile(String message) {
        File logfile = new File(System.getProperty("user.dir") + "/log.txt");

        // write message to file
        try(FileWriter writer = new FileWriter(logfile, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Log general program information.
     * @param message message
     */
    public static void log(String message) {
        String formattedMessage = LocalDateTime.now().format(LOCAL_TIME) + " [INFO] " + message;
        System.out.println(formattedMessage);
        logToFile(formattedMessage);
    }

    /**
     * Log a recoverable error.
     * @param warning message
     */
    public static void warn(String warning) {
        String formattedMessage = LocalDateTime.now().format(LOCAL_TIME) + " [WARN] " + warning;
        System.out.println(YELLOW + formattedMessage + RESET);
        logToFile(formattedMessage);
    }

    /**
     * Log an unrecoverable error.
     * @param error message
     */
    public static void error(String error) {
        String formattedMessage = LocalDateTime.now().format(LOCAL_TIME) + " [ERROR] " + error;
        System.out.println(RED + formattedMessage + RESET);
        logToFile(formattedMessage);
    }

    /**
     * Reset the logfile.
     */
    public static void reset() {
        File logfile = new File(System.getProperty("user.dir") + "/log.txt");

        // create file if it doesn't already exist
        try {
            logfile.getParentFile().mkdirs();
            logfile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // clear it
        try {
            FileWriter writer = new FileWriter(logfile, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
