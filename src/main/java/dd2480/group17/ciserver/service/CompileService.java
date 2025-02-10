package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.MavenExecutor;
import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.utils.Logger;

public class CompileService {

    private static final MavenExecutor mavenExecutor = new MavenExecutor();
    private static final Logger logger = new Logger();

    public boolean compileCode(String repoPath, String commitId) {
        CompileDTO result = mavenExecutor.runCompile(repoPath);
        logger.logCompileEvent(commitId, result.getOutput(), result.getError());
        return result.isSuccess();
    }
}
