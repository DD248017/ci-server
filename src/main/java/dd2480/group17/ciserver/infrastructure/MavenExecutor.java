package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.utils.MavenCompileRunner;

public class MavenExecutor {

    public CompileDTO runCompile(String repoPath) {
        return MavenCompileRunner.runMavenCompile("compile", repoPath);
    }

    /*
     * public CompileDTO runTest(String repoPath) {
     * return MavenCompileRunner.runMaven("test", repoPath);
     * }
     */
}