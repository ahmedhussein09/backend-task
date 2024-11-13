package com.vision.task.utils.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vision.task.model.ErrorDto;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConfigError {

    private final ObjectMapper objectMapper;
    @Getter
    private static Map<String, ErrorDto> errorDtoMap = new HashMap<>();

    @PostConstruct
    public void init(){
        TypeReference<List<ErrorDto>> typeReference = new TypeReference<>() {};
        try(InputStream inputStream = TypeReference.class.getResourceAsStream("/files/error-map.json")){
            List<ErrorDto> errorDtos = objectMapper.readValue(inputStream, typeReference);
            errorDtoMap = errorDtos.stream().collect(Collectors.toMap(ErrorDto::getErrorKey, e -> e));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
