package com.vision.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePojo {
    private boolean success;
    private String messageEn;
    private String messageAr;
    private Object content;
}
