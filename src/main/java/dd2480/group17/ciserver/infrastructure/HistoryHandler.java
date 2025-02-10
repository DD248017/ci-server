package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import dd2480.group17.ciserver.infrastructure.dto.LogDTO;
import dd2480.group17.ciserver.service.HistoryService;

public class HistoryHandler extends AbstractHandler {

    private static final HistoryService historyService = new HistoryService();

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