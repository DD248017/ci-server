package dd2480.group17.ciserver.infrastructure.dto;

public class CompileDTO {
    private final boolean success;
    private final String output;
    private final String error;

    public CompileDTO(boolean success, String output, String error) {
        this.success = success;
        this.output = output;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }
}
