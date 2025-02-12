package dd2480.group17.ciserver.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class HistoryServiceTest {

    private Path tempLogsDir;

    // create temporary log directory before each test
    @BeforeEach
    void setUp() throws IOException {
        tempLogsDir = Files.createTempDirectory("templogs");
    }

    // delete the directory after each test
    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempLogsDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempLogsDir)) {
                for (Path file : stream) {
                    Files.delete(file);
                }
            }
            Files.delete(tempLogsDir);
        }
    }

    private void createLogFile(String fileName, String content) throws IOException {
        Path logFile = tempLogsDir.resolve(fileName);
        Files.write(logFile, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }

    @Test
    void testProcessAllLogsWhenLogsExist() throws IOException {
        createLogFile("log1.log", "Commit ID: compile1234\n" + //
                "Standard Output:\n" + //
                "Compilation successful\n" + //
                "Error Output:\n" + //
                "No errors");
        createLogFile("log2.log", "Commit ID: compile1234\n" + //
                "Standard Output:\n" + //
                "Compilation successful\n" + //
                "Error Output:\n" + //
                "No errors");

        HistoryService historyService = new HistoryService();
        List<LogDTO> logs = historyService.processAllLogsInDirectory(tempLogsDir.toString());

        assertNotNull(logs);
        assertEquals(2, logs.size(), "Expected two log files to be processed.");
    }

    @Test
    void testProcessAllLogsWhenDirectoryIsEmpty() {
        HistoryService historyService = new HistoryService();
        List<LogDTO> logs = historyService.processAllLogsInDirectory(tempLogsDir.toString());

        assertNotNull(logs);
        assertTrue(logs.isEmpty(), "Expected no logs to be processed in an empty directory.");
    }

    @Test
    void testProcessAllLogsWhenDirectoryDoesNotExist() {
        HistoryService historyService = new HistoryService();
        List<LogDTO> logs = historyService.processAllLogsInDirectory("non_existent_directory");

        assertNotNull(logs);
        assertTrue(logs.isEmpty(), "Expected empty list when directory does not exist.");
    }

    @Test
    void testProcessAllLogsWhenNonLogFilesExist() throws IOException {
        createLogFile("log1.log", "Commit ID: compile1234\n" + //
                "Standard Output:\n" + //
                "Compilation successful\n" + //
                "Error Output:\n" + //
                "No errors");
        createLogFile("log2.txt", "should be ignored");
        createLogFile("log3.csv", "should be ignored");

        HistoryService historyService = new HistoryService();
        List<LogDTO> logs = historyService.processAllLogsInDirectory(tempLogsDir.toString());

        assertNotNull(logs);
        assertEquals(1, logs.size(), "Only .log files should be processed.");
    }
}
