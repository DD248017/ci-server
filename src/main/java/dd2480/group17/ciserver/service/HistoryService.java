package dd2480.group17.ciserver.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;
import dd2480.group17.ciserver.utils.LogParser;

/**
 * The {@code HistoryService} class is responsible for processing log files
 * located in a specified directory. It parses each log file into a
 * {@link LogDTO}
 * object, which encapsulates details about the log entry.
 * <p>
 * This service is particularly useful in a Continuous Integration (CI)
 * environment
 * where build logs are stored and need to be combined for reporting or
 * debugging purposes.
 * </p>
 */
public class HistoryService {

    /**
     * Processes all log files within the specified directory path and returns a
     * list of log entries
     * 
     * @param directoryPath the file system path to the directory containing log
     *                      files
     * @return a list of objects representing the parsed log entries,
     *         return true empty list if no log files are found
     */
    public static List<LogDTO> processAllLogsInDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".log"));
        List<LogDTO> logEntries = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                LogDTO logDTO = LogParser.parseLogFile(file);
                logEntries.add(logDTO);
            }
        } else {
            System.out.println("No log files found in the directory.");
        }
        return logEntries;
    }

}
