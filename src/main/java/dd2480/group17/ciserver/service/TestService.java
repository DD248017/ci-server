package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.MavenExecutor;
import dd2480.group17.ciserver.infrastructure.dto.TestDTO;
import dd2480.group17.ciserver.utils.Logger;

public class TestService {

    private static final MavenExecutor mavenExecutor = new MavenExecutor();
    private static final Logger logger = new Logger();

    // TODO run tests
    public boolean executeTests(String repoPath, String commitId) {
        TestDTO result = mavenExecutor.runTest(repoPath);
        logger.logTestEvent(commitId, result.getOutput(), result.getFailedTests());
        return result.isSuccess();
    }

    // TODO get failed tests, (maybe we don't need this)
    /*
     * public List<String> getFailedTests(String repoPath) {
     * 
     * }
     */
}
