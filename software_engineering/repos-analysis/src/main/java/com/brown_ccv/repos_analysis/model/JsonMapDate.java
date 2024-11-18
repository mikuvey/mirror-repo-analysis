package com.brown_ccv.repos_analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// @Data
// public class JsonMapDate {
//     @JsonProperty("commit.author.date")
//     private String date;
// }

@Data
public class JsonMapDate {
    @JsonProperty("commit")
    private Commit commit;

    @Data
    public static class Commit {
        @JsonProperty("author")
        private Author author;

        @Data
        public static class Author {
            @JsonProperty("date")
            private String date;
        }
    }
}

