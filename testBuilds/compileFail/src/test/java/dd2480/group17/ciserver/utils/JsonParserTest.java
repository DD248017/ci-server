package dd2480.group17.ciserver.utils;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonParserTest {

    @Test
    public void testParseFromJsonFile() throws IOException {
        String filePath = "src/main/resources/dd2480/group17/ciserver/logs/webhook/2025-02-07_21-02-45_webhook-4e4b052.json";
        File testJsonFile = new File(filePath);

        JsonParser parser = new JsonParser();
        WebhookDTO dto = parser.parse(testJsonFile);

        assertEquals("refs/heads/test", dto.ref());
        assertEquals("73b816bf9eb749d4f9279d4b95db12894ecbc4f3", dto.before());
        assertEquals("a3b7b6e195e6bd387747b40156e503a9d8ebdca1", dto.after());
        assertEquals("DD248017/ci-server", dto.repository().fullName());
        assertEquals("https://github.com/DD248017/ci-server", dto.repository().htmlUrl());
        assertEquals("a3b7b6e195e6bd387747b40156e503a9d8ebdca1", dto.headCommit().id());
        assertEquals("Update README.md\n\ntest json parse", dto.headCommit().message());
        assertEquals("maxaik", dto.headCommit().author().name());
        assertEquals("test", dto.branch());
    }
}
