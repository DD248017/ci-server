package dd2480.group17.ciserver.utils;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;

/**
 * JsonParser is responsible for parsing JSON files into Java objects.
 */
public class JsonParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Parses a local JSON file into a {@link WebhookDTO} object.
     *
     * @param file The JSON file to be parsed.
     * @return A {@link WebhookDTO} object representing the contents of the JSON
     *         file.
     * @throws IOException If parsing the file fails.
     */
    public WebhookDTO parse(File file) throws IOException {
        return OBJECT_MAPPER.readValue(file, WebhookDTO.class);
    }
}