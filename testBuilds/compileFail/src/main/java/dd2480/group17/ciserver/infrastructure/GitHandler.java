package dd2480.group17.ciserver.infrastructure;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * The {@code GitHandler} class provides utility methods for performing Git
 * operations,
 * such as cloning a repository, pulling the latest changes, and checking out a
 * specific commit.
 * <p>
 * Currently, only the repository cloning functionality is implemented. Future
 * enhancements
 * will include pulling the latest changes and checking out to a specific commit
 * hash.
 * </p>
 */
public class GitHandler {

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
}
