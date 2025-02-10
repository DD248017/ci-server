package dd2480.group17.ciserver.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;

/**
 * LogParser is responsible for parsing log files and extracting relevant
 * information.
 * It reads log files and extracts the commit ID and content.
 */
public class LogParser {
    /**
     * Parses a log file and extracts the commit ID and log content.
     *
     * @param file The log file to be parsed.
     * @return A {@link LogDTO} object containing the commit ID, log content, and
     *         filename.
     */
    public static LogDTO parseLogFile(File file) {
        String line;
        String content = "";
        String commitId = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Commit ID:")) {
                    commitId = line.substring(11).trim();
                } else {
                    line = line.trim();
                    content += line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return new LogDTO(commitId, content, file.getName());
    }
}
