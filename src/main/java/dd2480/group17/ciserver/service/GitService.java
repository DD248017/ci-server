package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.GitHandler;

public class GitService {

    private static final GitHandler gitHandler = new GitHandler();

    /**
     * Fetches the repository by cloning it from the specified URL, branch and
     * target directory
     * 
     * @param repoUrl   the url of the repository
     * @param branch    the branch to clone
     * @param targetDir the directory where the repository should be cloned
     * @return return true if the repository was successfully cloned, false
     *         otherwise
     */
    public boolean fetchRepository(String repoUrl, String branch, String targetDir) {

        boolean successful = gitHandler.cloneRepository(repoUrl, branch, targetDir);

        return successful;
    }

}
