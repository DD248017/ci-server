package dd2480.group17.ciserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@code GitServiceTest} class contains unit tests for the
 * {@link GitService} class.
 * <p>
 * It verifies that the
 * {@link GitService#fetchRepository(String, String, String)} method behaves
 * correctly:
 * <ul>
 * <li>It should clone a repository successfully when provided with a valid
 * repository URL and branch.</li>
 * <li>It should fail to clone when provided with an invalid repository
 * URL.</li>
 * </ul>
 * Additionally, it sets up a temporary local Git repository for testing
 * purposes and cleans up any temporary
 * directories after each test.
 * </p>
 */
public class GitServiceTest {

    private GitService gitServiceUnderTest;
    private final String url = "https://github.com/DD248017/ci-server";
    private File tempCloneDestination;

    /**
     * Sets up the test environment before each test method is run.
     * <p>
     * This method creates:
     * <ol>
     * <li>A new instance of {@link GitService} for testing.</li>
     * <li>A temporary local repository in which a test file is created and
     * committed.</li>
     * <li>A temporary directory to be used as the clone destination.</li>
     * </ol>
     * </p>
     *
     * @throws GitAPIException if an error occurs during Git operations.
     * @throws IOException     if an error occurs during file operations.
     */
    @BeforeEach
    public void initializeTestEnvironment() throws GitAPIException, IOException {
        gitServiceUnderTest = new GitService();
        tempCloneDestination = Files.createTempDirectory("tempCloneDestination").toFile();
    }

    /**
     * Cleans up the test environment after each test method is run.
     * <p>
     * This method deletes the temporary directories created during the tests,
     * ensuring that each test runs in a clean environment.
     * </p>
     */
    @AfterEach
    public void cleanTestEnvironment() {

        deleteDirectory(tempCloneDestination);
    }

    /**
     * Tests that the repository is cloned successfully when a valid repository URL
     * and branch are provided.
     * <p>
     * The test retrieves the URI of the temporary local repository, then attempts
     * to clone it into the temporary
     * clone destination directory. It asserts that the cloning process returns
     * {@code true} and verifies that the
     * cloned directory contains a ".git" folder, which indicates a valid Git
     * repository.
     * </p>
     */
    @Test
    public void shouldCloneRepositorySuccessfully() {

        String branch = "main";

        boolean result = gitServiceUnderTest.fetchRepository(url, branch, tempCloneDestination.getAbsolutePath());
        assertTrue(result, "Expected the repository to be cloned successfully, but the cloning process failed.");

        // Verify that the .git directory exists in the clone target directory
        File gitDir = new File(tempCloneDestination, ".git");
        assertTrue(gitDir.exists() && gitDir.isDirectory(),
                "After cloning, the destination directory should contain a '.git' folder indicating a valid Git repository.");
    }

    /**
     * Tests that cloning fails when an invalid repository URL is provided.
     * <p>
     * The test passes an invalid repository URL to the
     * {@link GitService#fetchRepository(String, String, String)}
     * method and expects the method to return {@code false}.
     * </p>
     */
    @Test
    public void shouldFailToCloneInvalidRepository() {
        String repoUrl = "invalid_repo_url";
        String branch = "main";

        boolean result = gitServiceUnderTest.fetchRepository(repoUrl, branch, tempCloneDestination.getAbsolutePath());
        assertFalse(result, "Cloning should fail because the provided repository URL is invalid.");
    }

    /**
     * Utility method to recursively delete a directory.
     *
     * @param directory The directory to be deleted.
     */
    private void deleteDirectory(File directory) {
        if (directory != null && directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteDirectory(f);
                    } else {
                        f.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
