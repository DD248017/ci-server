package dd2480.group17.ciserver.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import dd2480.group17.ciserver.infrastructure.dto.TestDTO;

public class MavenTestRunner {
    public static TestDTO runMavenTest(String command, String path) {
        Invoker invoker = new DefaultInvoker();

        // Here you need to make an env variable that's called "MAVEN_HOME"
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

        invoker.setWorkingDirectory(new File(path));

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Collections.singletonList(command));
        request.setBatchMode(true);

        StringWriter outputWriter = new StringWriter();
        StringWriter errorWriter = new StringWriter();

        invoker.setOutputHandler(outputWriter::write);
        invoker.setErrorHandler(errorWriter::write);

        boolean success = false;
        try {
            InvocationResult result = invoker.execute(request);
            success = (result.getExitCode() == 0);
        } catch (MavenInvocationException e) {
            errorWriter.write(e.getMessage());
        }
        String fullOutput = outputWriter.toString();
        String failedTests = extractFailedTests(fullOutput);

        return new TestDTO(success, fullOutput, failedTests);
    }

    private static String extractFailedTests(String output) {
        StringBuilder failedTests = new StringBuilder();
        Pattern pattern = Pattern.compile("Failed tests:([\\s\\S]*?)\n\n", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(output);
        while (matcher.find()) {
            failedTests.append(matcher.group(1)).append("\n");
        }
        return failedTests.toString().trim().isEmpty() ? "No failed tests" : failedTests.toString();
    }
}
