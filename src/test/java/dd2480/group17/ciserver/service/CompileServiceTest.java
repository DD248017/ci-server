package dd2480.group17.ciserver.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CompileServiceTest {

    private static final CompileService compileService = new CompileService();

    @Test
    void testCompileSuccess() {

        String repoPath = "./testBuilds/compileSuccess";
        String commitId = "some test id";

        boolean result = compileService.compileCode(repoPath, commitId);

        assertTrue(result, "Compilation should be successful");

    }

    @Test
    void testCompileFail() {

        String repoPath = "./testBuilds/compileFail";
        String commitId = "some test id";

        boolean result = compileService.compileCode(repoPath, commitId);

        assertFalse(result, "Compilation should be fail");

    }

}
