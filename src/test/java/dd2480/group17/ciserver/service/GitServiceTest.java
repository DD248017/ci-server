package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.service.GitService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GitServiceTest {

    private GitService gitService;
    private File localRepoDir;
    private File cloneTargetDir;

    @BeforeEach
    public void setup() throws GitAPIException, IOException {
        gitService = new GitService();

        // Initialize a local repository in a temporary directory
        localRepoDir = Files.createTempDirectory("localRepo").toFile();
        Git.init().setDirectory(localRepoDir).call();

        // Create a dummy file in the repository and commit it
        File dummyFile = new File(localRepoDir, "dummy.txt");
        Files.write(dummyFile.toPath(), "dummy content".getBytes());
        try (Git git = Git.open(localRepoDir)) {
            git.add().addFilepattern("dummy.txt").call();
            git.commit().setMessage("Initial commit").call();
        }

        // Create a temporary directory for the clone target
        cloneTargetDir = Files.createTempDirectory("cloneTarget").toFile();
    }

    @AfterEach
    public void tearDown() {
        // Delete the temporary directories created during the tests
        deleteDirectory(localRepoDir);
        deleteDirectory(cloneTargetDir);
    }

    @Test
    public void testFetchRepositorySuccess() {
        // Get the URI of the local repository (file:// URI)
        String repoUrl = localRepoDir.toURI().toString();
        String branch = "master"; // Default branch for Git.init() (or "master" in older versions)

        // Attempt to clone the repository into the target directory
        boolean result = gitService.fetchRepository(repoUrl, branch, cloneTargetDir.getAbsolutePath());
        assertTrue(result, "Repository cloning should succeed.");

        // Verify that the .git directory exists in the clone target directory
        File gitDir = new File(cloneTargetDir, ".git");
        assertTrue(gitDir.exists() && gitDir.isDirectory(), "The clone target must contain a .git directory.");
    }

    @Test
    public void testFetchRepositoryFailure() {
        // Specify an invalid repository URL
        String repoUrl = "invalid_repo_url";
        String branch = "master";

        // Attempt to clone the repository with an invalid URL, which should fail
        boolean result = gitService.fetchRepository(repoUrl, branch, cloneTargetDir.getAbsolutePath());
        assertFalse(result, "Cloning should fail for an invalid repository URL.");
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
