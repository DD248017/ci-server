package dd2480.group17.ciserver.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;
import dd2480.group17.ciserver.utils.LogParser;

public class HistoryService {

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
