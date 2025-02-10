package dd2480.group17.ciserver.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.Collections;

import org.apache.maven.shared.invoker.*;

import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;

/**
 * Utility class for running Maven compilation commands programmatically.
 * This class invokes Maven using the Apache Maven Invoker API and returns
 * the result of the compilation process.
 */
public class MavenCompileRunner {

    /**
     * Executes a Maven command on the specified project path.
     *
     * @param command The Maven goal to execute (e.g., "compile", "test").
     * @param path    The file system path to the project directory containing the
     *                pom.xml.
     * @return A {@link CompileDTO} object containing the success status,
     *         standard output, and error output of the Maven execution.
     */
    public static CompileDTO runMavenCompile(String command, String path) {
        Invoker invoker = new DefaultInvoker();

        // Set Maven home directory from the environment variable "MAVEN_HOME"
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

        // Set the working directory for the Maven invocation
        invoker.setWorkingDirectory(new File(path));

        // Configure the Maven invocation request
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Collections.singletonList(command));
        request.setBatchMode(true);

        // Capture output and error streams
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

        // Return the result as a CompileDTO
        return new CompileDTO(success, outputWriter.toString(), errorWriter.toString());
    }
}
