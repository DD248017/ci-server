package dd2480.group17.ciserver.infrastructure.dto;

/**
 * Data Transfer Object (DTO) representing the result of a compilation process.
 */
public class CompileDTO {
    private final boolean success;
    private final String output;
    private final String error;

    /**
     * Constructs a {@code CompileDTO} with the given compilation result details.
     *
     * @param success {@code true} if the compilation was successful, {@code false}
     *                otherwise.
     * @param output  the standard output generated during compilation.
     * @param error   the error message generated during compilation, if any.
     */
    public CompileDTO(boolean success, String output, String error) {
        this.success = success;
        this.output = output;
        this.error = error;
    }

    /**
     * Returns whether the compilation was successful.
     *
     * @return {@code true} if the compilation succeeded, {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the standard output of the compilation process.
     *
     * @return the output produced during compilation.
     */
    public String getOutput() {
        return output;
    }

    /**
     * Returns the error message from the compilation process, if any.
     *
     * @return the error message, or an empty string if no errors occurred.
     */
    public String getError() {
        return error;
    }
}

