package dd2480.group17.ciserver.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;

public class MavenCompileRunner {
    public static CompileDTO runMavenCompile(String command, String path) {
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

        System.out.println(success);
        return new CompileDTO(success, outputWriter.toString(), errorWriter.toString());
    }
}
