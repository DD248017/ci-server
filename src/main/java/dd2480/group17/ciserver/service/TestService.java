package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.MavenExecutor;
import dd2480.group17.ciserver.infrastructure.dto.TestDTO;
import dd2480.group17.ciserver.utils.Logger;

/**
 * The {@code TestService} class is responsible for executing the automated test
 * suite
 * for the project using Maven and logging the test results.
 * <p>
 * It leverages the {@link MavenExecutor} to run the tests and uses the
 * {@link Logger}
 * to record the output and any failed test details. This service plays a
 * critical role in the
 * Continuous Integration (CI) process by verifying that code changes meet the
 * quality standards.
 * </p>
 */
public class TestService {

    private static final MavenExecutor mavenExecutor = new MavenExecutor();
    private static final Logger logger = new Logger();

    /**
     * Execute the test suite for the project located at the specified repository
     * path
     * 
     * @param repoPath the file system path to the repository containing the project
     *                 to be tested
     * @param commitId the commit hash that identifies the code state to be tested
     * @return return true if all tests pass successfully, false if tests failed
     */

    public boolean executeTests(String repoPath, String commitId) {
        TestDTO result = mavenExecutor.runTest(repoPath);
        logger.logTestEvent(commitId, result.getOutput(), result.getFailedTests());
        return result.isSuccess();
    }
}
