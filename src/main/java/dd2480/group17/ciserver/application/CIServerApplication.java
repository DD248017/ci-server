package dd2480.group17.ciserver.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import dd2480.group17.ciserver.utils.MavenTestRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class CIServerApplication {
    /**
     * Skeleton of a ContinuousIntegrationServer which acts as webhook
     * See the Jetty documentation for API documentation of those classes.
     */
    public static class CiServer extends AbstractHandler {

        @Override
        public void handle(String target,
                Request baseRequest,
                HttpServletRequest request,
                HttpServletResponse response)
                throws IOException, ServletException {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);

            System.out.println("Received request at target: " + target);

            StringBuilder requestBody = new StringBuilder();
            try (BufferedReader reader = request.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(requestBody.toString());

            System.out.println("Webhook Payload: " + jsonNode.toPrettyString());

            System.out.println("Starting test");
            MavenTestRunner.runMavenTest();

            response.getWriter().println("Test job done");

        }

        public static void main(String[] args) throws Exception {

            Server server = new Server(8017);
            server.setHandler(new CiServer());
            server.start();
            server.join();
        }
    }
}
