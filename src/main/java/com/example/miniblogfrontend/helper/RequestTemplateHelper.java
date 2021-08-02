package com.example.miniblogfrontend.helper;

import com.example.miniblogfrontend.management.CurrentUserHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RequestTemplateHelper<T> {

    private final CurrentUserHolder currentUserHolder;

    public RequestTemplateHelper(CurrentUserHolder currentUserHolder) {
        this.currentUserHolder = currentUserHolder;
    }

    public HttpEntity<?> getRequestPayload() {
        HttpHeaders headers = buildHeaders();
        return new HttpEntity<>(headers);
    }

    public HttpEntity<T> getRequestPayload(T dto) {
        HttpHeaders headers = buildHeaders();
        return new HttpEntity<>(dto, headers);
    }

    public HttpEntity<T> getRequestPayloadWithAuthorization(T dto) {
        HttpHeaders headers = buildHeadersWithAuthorization();
        return new HttpEntity<>(dto, headers);
    }

    public HttpEntity<?> getRequestPayloadWithAuthorization() {
        HttpHeaders headers = buildHeadersWithAuthorization();
        return new HttpEntity<>(headers);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private HttpHeaders buildHeadersWithAuthorization() {
        HttpHeaders headers = buildHeaders();
        headers.set("Authorization", "Bearer " + currentUserHolder.getUserToken());
        return headers;
    }

}
