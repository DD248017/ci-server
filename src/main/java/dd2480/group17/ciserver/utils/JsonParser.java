package dd2480.group17.ciserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;

import java.io.File;
import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Parse local JSON file
     *
     * @param file JSON file
     * @return {@link WebhookDTO} object
     * @throws IOException parsing failed
     */
    public WebhookDTO parse(File file) throws IOException {
        return OBJECT_MAPPER.readValue(file, WebhookDTO.class);
    }
}
