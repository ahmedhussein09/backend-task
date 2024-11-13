package com.vision.task.exception;

import com.vision.task.model.ErrorDto;
import com.vision.task.model.ResponsePojo;
import com.vision.task.utils.files.ConfigError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponsePojo> handleException(Exception ex) {
        ErrorDto errorDto = ConfigError.getErrorDtoMap().get(ex.getMessage());
        /*if (errorDto.getErrorKey().equals(DeramanConst.ALREADY_DEACTIVATED) || errorDto.getErrorKey().equals(DeramanConst.ALREADY_ACTIVATED))
            return getResponseOfDeactivated(errorDto);
        else*/
            return getResponseEntity(errorDto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponsePojo> handleValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        log.error(ex.getBindingResult().getFieldErrors().stream().toString());
        Optional<String> error = ex.getBindingResult().getFieldErrors()
                .stream().findFirst().map(FieldError::getDefaultMessage);
        ErrorDto errorDto = ConfigError.getErrorDtoMap().get(error.get());
        return getResponseEntity(errorDto);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponsePojo> handleMissingParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ResponsePojo(false, ex.getMessage(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponsePojo> getResponseEntity(ErrorDto errorDto) {
        return new ResponseEntity<>(new ResponsePojo(false, errorDto.getMessageEn(),errorDto.getMessageAr(),null), HttpStatus.BAD_REQUEST);
    }

   /* private ResponseEntity<ResponsePojo> getResponseOfDeactivated(ErrorDto errorDto) {
        return new ResponseEntity<>(new ResponsePojo(false, errorDto.getMessageEn(),errorDto.getMessageAr(),null), HttpStatus.NOT_FOUND);
    }*/
}
