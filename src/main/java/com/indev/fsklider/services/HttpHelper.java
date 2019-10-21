package com.indev.fsklider.services;

import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.results.DialogResult;
import com.taskadapter.redmineapi.bean.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpHelper {

    private final RestTemplate restTemplate = new RestTemplate();

    public void doPost(String url, Context context) {
        RestTemplate restTemplate = new RestTemplate();
        DialogResult req = context.getResult();
        HttpEntity<DialogResult> request = new HttpEntity<>(req);
        DialogResult dialogResult = restTemplate.postForObject(url, request, DialogResult.class);
    }

    public void doPost(ResponseEvent event) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ResponseEvent> request = new HttpEntity<>(event);
        ResponseEvent dialogResult = restTemplate.postForObject("http://192.168.1.74:8080/event", request, ResponseEvent.class);
    }

    public <T> T postRedMine(String url, String data, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic cGhvbmVCb3Q6MTIzNDEyMzQ=");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(data, headers);
        return restTemplate.postForObject(url, request, clazz);
    }
}
