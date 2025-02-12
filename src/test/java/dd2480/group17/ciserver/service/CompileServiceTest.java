package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.MavenExecutor;
import dd2480.group17.ciserver.infrastructure.dto.CompileDTO;
import dd2480.group17.ciserver.utils.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompileServiceTest {

    private CompileService compileService;
    private MavenExecutor mockMavenExecutor;
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        // Create mock objects for dependencies
        mockMavenExecutor = mock(MavenExecutor.class);
        mockLogger = mock(Logger.class);

        // Replace the real dependencies with mocks in CompileService
        compileService = new CompileService(mockMavenExecutor, mockLogger);
    }

    @Test
    void testCompileSuccess() {
        // Given
        String repoPath = "/Users/giacomoricco/desktop/ci-server";
        String commitId = "abc123";
        CompileDTO mockCompileResult = new CompileDTO(true, "Compilation successful", "");
        when(mockMavenExecutor.runCompile(repoPath)).thenReturn(mockCompileResult);

        // When
        boolean result = compileService.compileCode(repoPath, commitId);

        // Then
        assertTrue(result, "Compilation should be successful");
        verify(mockMavenExecutor).runCompile(repoPath); // Verify compile was executed

        // Capture arguments passed to logger
        ArgumentCaptor<String> commitCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> outputCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockLogger).logCompileEvent(commitCaptor.capture(), outputCaptor.capture(), errorCaptor.capture());

        assertEquals(commitId, commitCaptor.getValue(), "Commit ID should match");
        assertEquals("Compilation successful", outputCaptor.getValue(), "Output should match");
        assertEquals("", errorCaptor.getValue(), "Error should be empty");
    }

    @Test
    void testCompileFailure() {
        // Given
        String repoPath = "/Users/giacomoricco/desktop/ci-server";
        String commitId = "xyz789";
        CompileDTO mockCompileResult = new CompileDTO(false, "", "Syntax error in Main.java");
        when(mockMavenExecutor.runCompile(repoPath)).thenReturn(mockCompileResult);

        // When
        boolean result = compileService.compileCode(repoPath, commitId);

        // Then
        assertFalse(result, "Compilation should fail");
        verify(mockMavenExecutor).runCompile(repoPath); // Verify compile was executed

        // Capture arguments passed to logger
        ArgumentCaptor<String> commitCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> outputCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> errorCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockLogger).logCompileEvent(commitCaptor.capture(), outputCaptor.capture(), errorCaptor.capture());

        assertEquals(commitId, commitCaptor.getValue(), "Commit ID should match");
        assertEquals("", outputCaptor.getValue(), "Output should be empty");
        assertEquals("Syntax error in Main.java", errorCaptor.getValue(), "Error message should match");
    }
}
