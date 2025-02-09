package dd2480.group17.ciserver.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WebhookDTO(
        @JsonProperty("ref") String ref,
        @JsonProperty("before") String before,
        @JsonProperty("after") String after,
        @JsonProperty("repository") Repository repository,
        @JsonProperty("head_commit") Commit headCommit
) {
    public String branch() {
        return ref != null ? ref.replace("refs/heads/", "") : "unknown";
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Repository(
            @JsonProperty("full_name") String fullName,
            @JsonProperty("html_url") String htmlUrl
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Commit(
            @JsonProperty("id") String id,
            @JsonProperty("message") String message,
            @JsonProperty("url") String url,
            @JsonProperty("author") Author author
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Author(
            @JsonProperty("name") String name,
            @JsonProperty("email") String email
    ) {}
}