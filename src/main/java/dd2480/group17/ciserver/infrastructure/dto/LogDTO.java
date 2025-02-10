package dd2480.group17.ciserver.infrastructure.dto;

public class LogDTO {
    private String commitId;
    private String errorOutput;
    private String filename;

    public LogDTO(String commitId, String errorOutput, String filename) {
        this.commitId = commitId;
        this.errorOutput = errorOutput;
        this.filename = filename;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getErrorOutput() {
        return errorOutput;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "Commit ID: " + commitId + "\n" +
                "Error Output: " + errorOutput + "\n" +
                "Filename: " + filename + "\n====================";
    }
}
