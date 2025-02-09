package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.utils.MavenCompileRunner;
import dd2480.group17.ciserver.utils.MavenTestRunner;

public class MavenExecutor {

    // Function to compile Maven
    public void runCompile() {
        System.out.println("Starting Maven compilation...");
        MavenCompileRunner.runMavenCompile();
    }

    // Function to test Maven
    public void runTest() {
        System.out.println("Starting Maven tests...");
        MavenTestRunner.runMavenTest();
    }

    // Function to compile and test Maven
    public void runCompileAndTest() {
        runCompile();
        runTest();
    }
}