package dd2480.group17.ciserver.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {

    @Test
    public void testWebhookLogging() throws IOException {
        String commitId = "test1234";
        String jsonData = "{\"test\":\"data\"}";
        String logMessage = "Webhook received for test commit";

        Logger logger = new Logger();
        String filepath = logger.logWebhookEvent(commitId, jsonData, logMessage);
        assertTrue(Files.exists(Paths.get(filepath)), "Log file should exist: " + filepath);

        String expectedContent = "=== Webhook Event (test) ===\n" +
                "Commit ID: test1234\n" +
                "Webhook received for test commit\n" +
                "Raw JSON Data:\n" +
                "{\"test\":\"data\"}\n" +
                "=====================\n";
        String actualContent = Files.readString(Paths.get(filepath));
        assertEquals(expectedContent, actualContent, "Log file content does not match expected content!");

    }

    @Test
    public void testCompileLogging() throws IOException {
        String commitId = "compile1234";
        String output = "Compilation successful";
        String error = "";

        Logger logger = new Logger();
        String filepath = logger.logCompileEvent(commitId, output, error);
        assertTrue(Files.exists(Paths.get(filepath)), "Log file should exist: " + filepath);

        String expectedContent = "=== Maven Compile Log (test) ===\n" +
                "Commit ID: compile1234\n" +
                "Standard Output:\n" +
                "Compilation successful\n" +
                "Error Output:\n" +
                "No errors\n" +
                "=========================\n";
        String actualContent = Files.readString(Paths.get(filepath));
        assertEquals(expectedContent, actualContent, "Log file content does not match expected content!");
    }

    @Test
    public void testTestLogging() throws IOException {
        String commitId = "test4567";
        String output = "Tests passed";
        String error = "Test X failed";

        Logger logger = new Logger();
        String filepath = logger.logTestEvent(commitId, output, error);
        assertTrue(Files.exists(Paths.get(filepath)), "Log file should exist: " + filepath);

        String expectedContent = "=== Maven Test Log (test) ===\n" +
                "Commit ID: test4567\n" +
                "Standard Output:\n" +
                "Tests passed\n" +
                "Error Output:\n" +
                "Test X failed\n" +
                "=====================\n";
        String actualContent = Files.readString(Paths.get(filepath));
        assertEquals(expectedContent, actualContent, "Log file content does not match expected content!");
    }
}
