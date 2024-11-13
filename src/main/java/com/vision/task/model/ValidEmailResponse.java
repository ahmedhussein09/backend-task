package com.vision.task.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ValidEmailResponse {
    private String email;
    private String autocorrect;
    private String deliverability;
    private String qualityScore;
    private IsValidFormat is_valid_format;

    @Setter
    @Getter
    public static class IsValidFormat {
        private boolean value;
        private String text;
    }
}
