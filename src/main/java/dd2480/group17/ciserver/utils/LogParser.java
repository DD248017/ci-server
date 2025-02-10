package dd2480.group17.ciserver.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;

public class LogParser {
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
