package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;
import dd2480.group17.ciserver.service.HistoryService;

/**
 * Handler for serving the history of compile and test logs.
 * This class processes log files and displays their contents as an HTML
 * response.
 */
public class HistoryHandler extends AbstractHandler {

    /**
     * Service responsible for processing log files.
     */
    private static final HistoryService historyService = new HistoryService();

    private List<LogDTO> webhookLogEntries;
    private List<LogDTO> compileLogEntries;
    private List<LogDTO> testLogEntries;

    /**
     * Handles incoming HTTP requests to retrieve and display log histories.
     *
     * @param target      the target of the request, which is the request URI
     * @param baseRequest the Jetty request object
     * @param request     the HttpServletRequest object
     * @param response    the HttpServletResponse object
     * @throws IOException if an input or output error occurs while handling the
     *                     request
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String webhookLogPath = "./src/main/resources/dd2480/group17/ciserver/logs/webhook";
        String compileLogPath = "./src/main/resources/dd2480/group17/ciserver/logs/compile";
        String testLogPath = "./src/main/resources/dd2480/group17/ciserver/logs/test";

        webhookLogEntries = historyService.processAllLogsInDirectory(webhookLogPath);
        compileLogEntries = historyService.processAllLogsInDirectory(compileLogPath);
        testLogEntries = historyService.processAllLogsInDirectory(testLogPath);

        String[] pathParts = request.getRequestURI().split("/");
        String pageContent;

        boolean allHistory = pathParts.length == 2;
        if (allHistory) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);

            pageContent = displayAllLogs("webhook") + displayAllLogs("compile") + displayAllLogs("test");
            response.getWriter().println(pageContent);
            return;
        }

        boolean legalDetailedHistory = legalDetailedHistory(pathParts);
        if (legalDetailedHistory) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);

            String type = pathParts[2];
            String commitId = pathParts[3];
            pageContent = displayOneLog(type, commitId);
            response.getWriter().println(pageContent);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid URL");

    }

    /**
     * Generates an HTML table displaying all webhook/compile/test log entries.
     *
     * @param type the type of log to display, either "webhook", "compile", or "test"
     * @return an HTML string representing the test log history
     */
    private String displayAllLogs(String type) {
        String html = "<h1>" + type.substring(0, 1).toUpperCase() + type.substring(1) + " log history</h1>";
        html += "<table border='1'><tr><th>Commit Id</th><th>Filename</th><th>Link</th></tr>";

        List<LogDTO> logEntries = switch (type) {
            case "webhook" -> webhookLogEntries;
            case "compile" -> compileLogEntries;
            case "test" -> testLogEntries;
            default -> null;
        };
        for (LogDTO entry : logEntries) {
            html += ("<tr>" + "<td>" + entry.getCommitId() + "</td>"
                    + "<td>" + entry.getFilename() + "</td>"
                    + "<td><a href='/history/" + type + "/" + entry.getCommitId() + "'>link</a></td>"
                    + "</tr>");
        }

        html += "</table>";
        return html;
    }

    /**
     * Generates an HTML table displaying a single log entry.
     *
     * @param type     the type of log to display, either "webhook", "compile", or "test"
     * @param commitId the commit ID to display
     * @return an HTML string representing the webhook log history
     */
    private String displayOneLog(String type, String commitId) {
        String html = "<h1>" + type.substring(0, 1).toUpperCase() + type.substring(1) + " log history</h1>";
        html += "<table border='1'><tr><th>Commit Id</th><th>Filename</th><th>Content</th></tr>";

        List<LogDTO> logEntries = switch (type) {
            case "webhook" -> webhookLogEntries;
            case "compile" -> compileLogEntries;
            case "test" -> testLogEntries;
            default -> null;
        };
        if (logEntries != null) {
            for (LogDTO entry : logEntries) {
                if (entry.getCommitId().equals(commitId)) {
                    html += ("<tr>" + "<td>" + entry.getCommitId() + "</td>"
                            + "<td>" + entry.getFilename() + "</td>"
                            + "<td>" + entry.getErrorOutput() + "</td>"
                            + "</tr>");
                    break;
                }
            }
        } else {
            return "No log entries found";
        }

        html += "</table>";
        return html;
    }

    private boolean legalDetailedHistory(String[] pathParts) {
        if(pathParts.length != 4) {
            return false;
        }

        String type = pathParts[2];
        String commitId = pathParts[3];
        if (type.matches("webhook|compile|test")) {
            switch (type) {
                case "webhook":
                    return historyService.commitIdExists(commitId, webhookLogEntries);
                case "compile":
                    return historyService.commitIdExists(commitId, compileLogEntries);
                case "test":
                    return historyService.commitIdExists(commitId, testLogEntries);
            }
        }

        return false;
    }
}