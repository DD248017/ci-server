package dd2480.group17.ciserver.utils;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class MavenCompileRunner {
    public static void runMavenCompile() {
        Invoker invoker = new DefaultInvoker();

        // Here you need to make an env variable that's called "MAVEN_HOME"
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

        invoker.setWorkingDirectory(new File("."));

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Collections.singletonList("compile"));

        try {
            InvocationResult result = invoker.execute(request);
            if (result.getExitCode() == 0) {
                System.out.println("Compilation was successful!");
            } else {
                System.out.println("Compilation failed!");
            }
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}


