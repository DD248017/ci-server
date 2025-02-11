package dd2480.group17.ciserver.infrastructure.dto;

/**
 * Data Transfer Object (DTO) for test results.
 * This class encapsulates information about the success of a test execution,
 * the output produced, and any failed test cases.
 */
public class TestDTO {
    
    /** Indicates whether the test execution was successful. */
    private final boolean success;
    
    /** The output produced by the test execution. */
    private final String output;
    
    /** A string representing failed test cases, if any. */
    private final String failedTests;

    /**
     * Constructs a new TestDTO instance with the given parameters.
     * 
     * @param success whether the test execution was successful
     * @param output the output of the test execution
     * @param failedTests a string representing failed test cases
     */
    public TestDTO(boolean success, String output, String failedTests) {
        this.success = success;
        this.output = output;
        this.failedTests = failedTests;
    }

    /**
     * Returns whether the test execution was successful.
     * 
     * @return {@code true} if the test execution was successful, {@code false} otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the output produced by the test execution.
     * 
     * @return the output of the test execution
     */
    public String getOutput() {
        return output;
    }

    /**
     * Returns a string representing failed test cases.
     * 
     * @return a string containing information about failed test cases
     */
    public String getFailedTests() {
        return failedTests;
    }
}

