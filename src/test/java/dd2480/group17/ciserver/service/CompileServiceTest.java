package dd2480.group17.ciserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CompileServiceTest {

    private static final CompileService compileService = new CompileService();

    @Test
    void testCompileSuccess() throws IOException {
        String zipFilePath = "./testBuilds.zip";
        String extractedFolderPath = "./extractedTestBuilds";
        String repoPath = "./extractedTestBuilds/testBuilds/compileSuccess";
        String commitId = "some test id";

        unzip(zipFilePath, extractedFolderPath);

        boolean result = compileService.compileCode(repoPath, commitId);

        assertTrue(result, "Compilation should be successful");

        deleteDirectory(new File(extractedFolderPath));
    }

    @Test
    void testCompileFail() throws IOException {

        String zipFilePath = "./testBuilds.zip";
        String extractedFolderPath = "./extractedTestBuilds";
        String repoPath = "./extractedTestBuilds/testBuilds/compileFail";
        String commitId = "some test id";

        unzip(zipFilePath, extractedFolderPath);

        boolean result = compileService.compileCode(repoPath, commitId);

        assertFalse(result, "Compilation should be fail");

        deleteDirectory(new File(extractedFolderPath));

    }

    private void unzip(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists())
            dir.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(Files.newInputStream(Path.of(zipFilePath)))) {
            ZipEntry entry;
            while ((entry = zipIn.getNextEntry()) != null) {
                File file = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    Files.copy(zipIn, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                zipIn.closeEntry();
            }
        }
    }

    private void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File sub : file.listFiles()) {
                deleteDirectory(sub);
            }
        }
        file.delete();
    }

}
