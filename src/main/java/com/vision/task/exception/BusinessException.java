package com.vision.task.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }

}
