package dd2480.group17.ciserver.infrastructure.dto;

/**
 * Data Transfer Object (DTO) representing log information related to a commit.
 */
public class LogDTO {
    private String commitId;
    private String errorOutput;
    private String filename;

    /**
     * Constructs a {@code LogDTO} with the given commit log details.
     *
     * @param commitId    the unique identifier of the commit.
     * @param errorOutput the error output related to the commit, if any.
     * @param filename    the name of the file associated with the log entry.
     */
    public LogDTO(String commitId, String errorOutput, String filename) {
        this.commitId = commitId;
        this.errorOutput = errorOutput;
        this.filename = filename;
    }

    /**
     * Returns the unique identifier of the commit.
     *
     * @return the commit ID.
     */
    public String getCommitId() {
        return commitId;
    }

    /**
     * Returns the error output related to the commit, if any.
     *
     * @return the error output message.
     */
    public String getErrorOutput() {
        return errorOutput;
    }

    /**
     * Returns the filename associated with the log entry.
     *
     * @return the filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Returns a string representation of the log entry.
     *
     * @return a formatted string containing commit details.
     */
    @Override
    public String toString() {
        return "Commit ID: " + commitId + "\n" +
                "Error Output: " + errorOutput + "\n" +
                "Filename: " + filename + "\n====================";
    }
}
