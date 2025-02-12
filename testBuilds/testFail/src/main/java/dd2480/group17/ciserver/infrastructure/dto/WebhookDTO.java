package dd2480.group17.ciserver.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) representing a webhook payload from a version
 * control system.
 * This record encapsulates information about a repository event, such as a push
 * event.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WebhookDTO(
                @JsonProperty("ref") String ref,
                @JsonProperty("before") String before,
                @JsonProperty("after") String after,
                @JsonProperty("repository") Repository repository,
                @JsonProperty("head_commit") Commit headCommit) {
        /**
         * Extracts the branch name from the ref string.
         * 
         * @return the branch name if available, otherwise "unknown"
         */
        public String branch() {
                return ref != null ? ref.replace("refs/heads/", "") : "unknown";
        }

        /**
         * Represents repository details included in the webhook payload.
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Repository(
                        @JsonProperty("full_name") String fullName,
                        @JsonProperty("html_url") String htmlUrl) {
        }

        /**
         * Represents commit details included in the webhook payload.
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Commit(
                        @JsonProperty("id") String id,
                        @JsonProperty("message") String message,
                        @JsonProperty("url") String url,
                        @JsonProperty("author") Author author) {
        }

        /**
         * Represents author details of a commit included in the webhook payload.
         */
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Author(
                        @JsonProperty("name") String name,
                        @JsonProperty("email") String email) {
        }
}
