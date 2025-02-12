package dd2480.group17.ciserver.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestServiceTest {

    private static final TestService testService = new TestService();

    @Test
    void testExecuteTestsSuccess() {

        String repoPath = "./testBuilds/testSuccess";
        String commitId = "some test id";

        boolean result = testService.executeTests(repoPath, commitId);

        assertTrue(result, "Compilation should be successful");

    }

    @Test
    void testExecuteTestsFail() {

        String repoPath = "./testBuilds/testFail";
        String commitId = "some test id";

        boolean result = testService.executeTests(repoPath, commitId);

        assertFalse(result, "Compilation should be fail");

    }

}
