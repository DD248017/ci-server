package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.MavenExecutor;
import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.utils.Logger;

/**
 * The {@code CompileService} class is responsible for compiling the code in a
 * specified repository path
 * using Maven and logging the compilation results.
 * <p>
 * This class represents the compilation process to {@link MavenExecutor} and
 * logs the output and errors using
 * {@link Logger}. It plays a critical role in the Continuous Integration (CI)
 * pipeline by determining whether
 * a build is successful or not.
 * </p>
 */
public class CompileService {

    private final MavenExecutor mavenExecutor;
    private final Logger logger;

    // Default constructor (used in production)
    public CompileService() {
        this.mavenExecutor = new MavenExecutor();
        this.logger = new Logger();
    }

    // Constructor for unit testing (allows passing mock objects)
    public CompileService(MavenExecutor mavenExecutor, Logger logger) {
        this.mavenExecutor = mavenExecutor;
        this.logger = logger;
    }

    /**
     * Compiles the code in the specified repository path and logs the result
     * 
     * @param repoPath the file system path to the repository that needs to be
     *                 compiled
     * @param commitId the commit hash used for identifying the build in the logs
     * @return true if the compilation is successful, false otherwise
     */
    public boolean compileCode(String repoPath, String commitId) {
        CompileDTO result = mavenExecutor.runCompile(repoPath);
        logger.logCompileEvent(commitId, result.getOutput(), result.getError());
        return result.isSuccess();
    }
}
