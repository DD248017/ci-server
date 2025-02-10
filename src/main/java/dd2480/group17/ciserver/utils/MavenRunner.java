package dd2480.group17.ciserver.utils;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class MavenRunner {
    public static boolean runMaven(String command, String path) {
        Invoker invoker = new DefaultInvoker();

        // Here you need to make an env variable that's called "MAVEN_HOME"
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));

        invoker.setWorkingDirectory(new File(path));

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Collections.singletonList(command));

        try {
            InvocationResult result = invoker.execute(request);
            if (result.getExitCode() == 0) {
                return true;
            } else {
                return false;
            }
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
