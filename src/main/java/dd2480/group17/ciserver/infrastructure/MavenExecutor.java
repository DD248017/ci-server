package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.infrastructure.dto.TestDTO;
import dd2480.group17.ciserver.utils.MavenCompileRunner;
import dd2480.group17.ciserver.utils.MavenTestRunner;

/**
 * The {@code MavenExecutor} class provides methods to execute Maven commands
 * for compiling and testing a project.
 */
public class MavenExecutor {

    /**
     * Runs the Maven compile command on the specified repository path.
     *
     * @param repoPath the path to the repository where the Maven compile command
     *                 should be executed
     * @return a {@link CompileDTO} object containing the result of the compilation
     *         process
     */
    public CompileDTO runCompile(String repoPath) {
        return MavenCompileRunner.runMavenCompile("compile", repoPath);
    }

    /**
     * Runs the Maven test command on the specified repository path.
     *
     * @param repoPath the path to the repository where the Maven test command
     *                 should be executed
     * @return a {@link TestDTO} object containing the result of the test execution
     */
    public TestDTO runTest(String repoPath) {
        return MavenTestRunner.runMavenTest("test", repoPath);
    }
}
