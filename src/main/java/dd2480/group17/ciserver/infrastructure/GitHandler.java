package dd2480.group17.ciserver.infrastructure;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class GitHandler {

    // TODO clone a repository and then send instructions to githubservice what to
    // do with data
    // TODO we need to add credentials

    /**
     * Clones a specific GitHub repository from a given branch.
     *
     * @param repoUrl   The URL of the repository (e.g.,
     *                  "https://github.com/user/repo.git").
     * @param branch    The specific branch to clone.
     * @param targetDir The directory where the repo should be cloned.
     * @return true if cloning is successful, false otherwise.
     */
    public static boolean cloneRepository(String repoUrl, String branch, String targetDir) {
        try {
            System.out.println("Cloning repository: " + repoUrl + " (Branch: " + branch + ")");

            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setBranch(branch)
                    .setDirectory(new File(targetDir))
                    .call();

            System.out.println("Repository cloned successfully to: " + targetDir);
            return true;
        } catch (GitAPIException e) {
            System.err.println("Error cloning repository: " + e.getMessage());
            return false;
        }
    }

    // TODO pull latest changes
    public void pullLatestChanges(String repoPath) {

    }

    // TODO checkout to the right commit hash
    /**
     * Checks out a specific commit in the given Git repository.
     *
     * @param repoPath   The local file system path to the Git repository.
     * @param commitHash The unique hash identifier of the commit to checkout.
     * @return true if checkout is successful, false otherwise.
     */
    public void checkoutCommit(String repoPath, String commitHash) {
        try {
            Git git = Git.open(new File(repoPath));

            git.checkout().setName(commitHash).call();

            System.out.println("Checked out to commit: " + commitHash);
        } catch (Exception e) {
            System.out.println("Error during checkout : " + e.getMessage());
        }
    }
}
