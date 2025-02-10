package dd2480.group17.ciserver.infrastructure.dto;

public class TestDTO {
    private final boolean success;
    private final String output;
    private final String failedTests;

    public TestDTO(boolean success, String output, String failedTests) {
        this.success = success;
        this.output = output;
        this.failedTests = failedTests;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOutput() {
        return output;
    }

    public String getFailedTests() {
        return failedTests;
    }
}
