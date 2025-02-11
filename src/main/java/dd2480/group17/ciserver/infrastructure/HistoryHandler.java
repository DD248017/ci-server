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

    /** Service responsible for processing log files. */
    private static final HistoryService historyService = new HistoryService();

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

        String compileLogPath = "./src/main/resources/dd2480/group17/ciserver/logs/compile";
        String testLogPath = "./src/main/resources/dd2480/group17/ciserver/logs/test";

        List<LogDTO> CompilelogEntries = historyService.processAllLogsInDirectory(compileLogPath);
        List<LogDTO> TestlogEntries = historyService.processAllLogsInDirectory(testLogPath);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String pageContent = displayCompileLogs(CompilelogEntries) + displayTestLogs(TestlogEntries);

        response.getWriter().println(pageContent);
    }

    /**
     * Generates an HTML table displaying compile log entries.
     * 
     * @param logEntries a list of compile log entries
     * @return an HTML string representing the compile log history
     */
    private String displayCompileLogs(List<LogDTO> logEntries) {
        String html = "<h1>Compile log history</h1>";
        html += "<table border='1'><tr><th>Commit Id</th><th>Filename</th><th>Content</th></tr>";

        for (LogDTO entry : logEntries) {
            html += ("<tr>" + "<td>" + entry.getCommitId() + "</td>"
                    + "<td>" + entry.getFilename() + "</td>"
                    + "<td>" + entry.getErrorOutput() + "</td>"
                    + "</tr>");
        }

        html += "</table>";
        return html;
    }

    /**
     * Generates an HTML table displaying test log entries.
     * 
     * @param logEntries a list of test log entries
     * @return an HTML string representing the test log history
     */
    private String displayTestLogs(List<LogDTO> logEntries) {
        String html = "<h1>Test log history</h1>";
        html += "<table border='1'><tr><th>Commit Id</th><th>Filename</th><th>Content</th></tr>";

        for (LogDTO entry : logEntries) {
            html += ("<tr>" + "<td>" + entry.getCommitId() + "</td>"
                    + "<td>" + entry.getFilename() + "</td>"
                    + "<td>" + entry.getErrorOutput() + "</td>"
                    + "</tr>");
        }

        html += "</table>";
        return html;
    }
}