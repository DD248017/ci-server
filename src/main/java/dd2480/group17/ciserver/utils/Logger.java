package dd2480.group17.ciserver.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger utility for CI Server.
 * This class handles logging for webhook events, mvn compile, and mvn test
 * logs.
 * Log format is loaded from log4j.properties.
 */
public class Logger {
    /**
     * Logs Webhook data using format from log4j.properties.
     *
     * @param commitId   the commit hash from webhook
     * @param jsonData   the raw JSON payload
     * @param logMessage processed webhook log message
     * @return the path to the saved log file
     */
    public String logWebhookEvent(String commitId, String jsonData, String logMessage) {
        String format = CONFIG_LOADER.getLogFormatWebhook();
        String logContent = format.replace("{commitId}", commitId)
                .replace("{message}", logMessage)
                .replace("{jsonData}", jsonData);
        return saveLogToFile("webhook", commitId, logContent);
    }

    /**
     * Logs the output of mvn compile using format from log4j.properties.
     *
     * @param commitId the commit hash
     * @param output   the output from the compilation process
     * @param error    the error output (if any)
     * @return the path to the saved log file
     */
    public String logCompileEvent(String commitId, String output, String error) {
        String format = CONFIG_LOADER.getLogFormatCompile();
        String logContent = format.replace("{commitId}", commitId)
                .replace("{output}", output)
                .replace("{error}", error.isEmpty() ? "No errors" : error);
        return saveLogToFile("compile", commitId, logContent);
    }

    /**
     * Logs the output of mvn test using format from log4j.properties.
     *
     * @param commitId the commit hash
     * @param output   the output from the test execution
     * @param error    the error output (if any)
     * @return the path to the saved log file
     */
    public String logTestEvent(String commitId, String output, String error) {
        String format = CONFIG_LOADER.getLogFormatTest();
        String logContent = format.replace("{commitId}", commitId)
                .replace("{output}", output)
                .replace("{error}", error.isEmpty() ? "No errors" : error);
        return saveLogToFile("test", commitId, logContent);
    }

    /**
     * Saves JSON data to a file.
     *
     * @param logType  type of log (webhook, compile, test)
     * @param commitId commit hash
     * @param jsonData JSON data to save
     * @return the path to the saved log file
     */
    public String saveJsonToFile(String logType, String commitId, String jsonData) {
        String logsBasePath = "src/main/resources/dd2480/group17/ciserver/logs/";
        String filename = generateJsonFilename(logType, commitId);
        String filepath = logsBasePath + logType + "/" + filename;

        try {
            Files.createDirectories(Paths.get(logsBasePath + logType + "/"));
            try (FileWriter writer = new FileWriter(filepath, true)) {
                writer.write(jsonData);
            }
        } catch (IOException e) {
            System.err.println("Failed to write " + logType + " log: " + e.getMessage());
        }

        return filepath;
    }

    private static final ConfigLoader CONFIG_LOADER = new ConfigLoader();

    /**
     * Saves log content to a file.
     *
     * @param logType    type of log (webhook, compile, test)
     * @param commitId   commit hash
     * @param logContent formatted log content
     * @return the path to the saved log file
     */
    private String saveLogToFile(String logType, String commitId, String logContent) {
        String logsBasePath = "src/main/resources/dd2480/group17/ciserver/logs/";
        String filename = generateLogFilename(logType, commitId);
        String filepath = logsBasePath + logType + "/" + filename;

        try {
            Files.createDirectories(Paths.get(logsBasePath + logType + "/"));
            try (FileWriter writer = new FileWriter(filepath, true)) {
                writer.write(logContent);
            }
        } catch (IOException e) {
            System.err.println("Failed to write " + logType + " log: " + e.getMessage());
        }

        return filepath;
    }

    /**
     * Generates a formatted timestamp for log filenames.
     *
     * @return formatted timestamp
     */
    private String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    /**
     * Generates a log filename based on type and commit ID.
     *
     * @param type     log type (webhook, compile, test)
     * @param commitId commit hash
     * @return formatted filename
     */
    private String generateLogFilename(String type, String commitId) {
        return String.format("%s_%s-%s.log", getTimestamp(), type, commitId.substring(0, 8));
    }

    private String generateJsonFilename(String type, String commitId) {
        return String.format("%s_%s-%s.json", getTimestamp(), type, commitId.substring(0, 8));
    }
}
