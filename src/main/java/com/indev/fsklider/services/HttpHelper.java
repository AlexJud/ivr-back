package com.indev.fsklider.services;

import com.indev.fsklider.beans.ResponseEvent;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.results.DialogResult;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpHelper {
    public void doPost(String url, Context context){
        RestTemplate restTemplate = new RestTemplate();
        DialogResult req = context.getResult();
        HttpEntity<DialogResult> request = new HttpEntity<>(req);
        DialogResult dialogResult = restTemplate.postForObject(url, request, DialogResult.class);
    }

    public void doPost (ResponseEvent event){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ResponseEvent> request = new HttpEntity<>(event);
        ResponseEvent dialogResult = restTemplate.postForObject("http://localhost:8080/event", request, ResponseEvent.class);
    }
}
