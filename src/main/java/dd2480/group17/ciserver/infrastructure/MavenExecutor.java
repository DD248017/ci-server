package dd2480.group17.ciserver.infrastructure;

import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.infrastructure.dto.TestDTO;
import dd2480.group17.ciserver.utils.MavenCompileRunner;
import dd2480.group17.ciserver.utils.MavenTestRunner;

public class MavenExecutor {

    public CompileDTO runCompile(String repoPath) {
        return MavenCompileRunner.runMavenCompile("compile", repoPath);
    }

    public TestDTO runTest(String repoPath) {
        return MavenTestRunner.runMavenTest("test", repoPath);
    }

}