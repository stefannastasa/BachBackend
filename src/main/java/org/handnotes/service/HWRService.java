package org.handnotes.service;


import org.handnotes.wss.WebSocketHandler;
import org.handnotes.wss.WsSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


@Service
public class HWRService {
    private final RestTemplate restTemplate;
    private final WebSocketHandler webSocketHandler;

    @Value("${hwr.url}")
    private String endpointUrl;

    public HWRService(RestTemplate restTemplate, WebSocketHandler webSocketHandler){
        this.restTemplate = restTemplate;
        this.webSocketHandler = webSocketHandler;
    }

    @Async
    public CompletableFuture<String> getPrediction(String userId, String noteId, List<String> signedUrls){
        System.out.println("Triggering the model to perform prediction.");
        String apiUrl = endpointUrl + "/predict";
        HttpHeaders headers = new HttpHeaders();
        Map<String, Object> requestBody = Map.of("urls", signedUrls);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try{
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            Map<String, Object> response = responseEntity.getBody();
            String prediction = response != null ? response.get("prediction").toString() : "No prediction";

            webSocketHandler.sendMessage(userId, String.format("noteId: %s\nprediction: %s", noteId, prediction));

            return AsyncResult.forValue(prediction).completable();
        }catch(HttpClientErrorException e){
            throw new RuntimeException("Failed to get predictions");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
