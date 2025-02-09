package dd2480.group17.ciserver.service;

import java.util.List;

public class HistoryService {

    public void logBuild(String repoUrl, String branch, String commitHash, boolean compileSuccess,
            boolean testSuccess) {

    }

    // TODO store build results
    public void storeBuildResult(String commitHash, String logs) {

    }

    // TODO store test results
    public void storeTestResults(String commitHash, boolean success, List<String> failedTests) {

    }

}
