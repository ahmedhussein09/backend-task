package com.vision.task.integration.impl;

import com.vision.task.exception.BusinessException;
import com.vision.task.integration.ThirdPartyIntegration;
import com.vision.task.model.ValidEmailResponse;
import com.vision.task.utils.files.TaskConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThirdPartyIntegrationImpl implements ThirdPartyIntegration {

    private final RestTemplate restTemplate;

    @Value("${valid.email.url}")
    private String validEmailUrl;

    @Override
    public boolean validationEmail(String email) {
        String urlWithParams = UriComponentsBuilder.fromHttpUrl(validEmailUrl)
                .queryParam("email", email)
                .queryParam("api_key", "f6519007982b40dc8e290cc9c9c83cf2")
                .toUriString();
        ValidEmailResponse validEmailResponse;
        log.info("URL: {}", urlWithParams);
        try {
            validEmailResponse = restTemplate.getForObject(urlWithParams, ValidEmailResponse.class);
            log.info("Valid: {}", validEmailResponse.getIs_valid_format().isValue());
        }catch (Exception exception){
            log.error(exception.getMessage());
            throw new BusinessException(TaskConst.GENERIC_ERROR);
        }
        return validEmailResponse.getIs_valid_format().isValue();
    }
}
