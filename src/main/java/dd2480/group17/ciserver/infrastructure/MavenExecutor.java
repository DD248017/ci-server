package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.utils.MavenCompileRunner;
import dd2480.group17.ciserver.utils.MavenTestRunner;

public class MavenExecutor {

    // Metodo per eseguire la compilazione Maven
    public void runCompile() {
        System.out.println("Starting Maven compilation...");
        MavenCompileRunner.runMavenCompile();
    }

    // Metodo per eseguire i test Maven
    public void runTest() {
        System.out.println("Starting Maven tests...");
        MavenTestRunner.runMavenTest();
    }

    // Metodo per eseguire entrambe le operazioni (compilazione e test)
    public void runAll() {
        runCompile();
        runTest();
    }

    public static void main(String[] args) {
        // Eseguiamo tutto: compilazione e test
        MavenExecutor executor = new MavenExecutor();
        executor.runAll();
    }
}