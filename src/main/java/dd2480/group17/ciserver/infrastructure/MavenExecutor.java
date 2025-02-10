package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.utils.MavenRunner;

public class MavenExecutor {

    public boolean runCompile(String repoPath) {
        return MavenRunner.runMaven("compile", repoPath);
    }

    public boolean runTest(String repoPath) {
        return MavenRunner.runMaven("test", repoPath);
    }
}