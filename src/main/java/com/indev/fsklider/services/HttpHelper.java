package com.indev.fsklider.services;

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
}
