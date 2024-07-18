package com.anys34.bssm24summer.service;

import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.domain.Objects;
import com.anys34.bssm24summer.domain.Saave;
import com.anys34.bssm24summer.domain.repository.ObjectRepository;
import com.anys34.bssm24summer.domain.repository.SaaveRepository;
import com.anys34.bssm24summer.feign.GPTClient;
import com.anys34.bssm24summer.feign.GPTRequest;
import com.anys34.bssm24summer.feign.WeatherInformationClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ObjectService {
    private final WeatherInformationClient weatherInformationClient;
    private final GPTClient gptClient;
    private final ObjectRepository objectRepository;
    private final MqttService.MqttGateway mqttGateway;
    private final SaaveRepository saaveRepository;

    @Value("${gpt.token}")
    private String token;
    private String mot = "1";

    public WeatherResponse weather() {
        Map<String, Object> response = weatherInformationClient
                .gerWeatherInformation();

        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        Map<String, Object> weathers = weatherList.get(0);
        Map<String, Object> main = (Map<String, Object>) response.get("main");

        String weather = (String) weathers.get("description");
        double temp = (Double) main.get("temp");
        return new WeatherResponse(weather, temp);
    }

    @Transactional
    public ObjectNode chat(String answer) throws JsonProcessingException {
        List<Objects> objects = objectRepository.findAll();
        StringBuilder list = new StringBuilder();
        for(Objects obj: objects) {
            list.append(", "+obj.getName());
        }
        System.out.println("list = " + list.substring(2));
        Map<String, Object> response = gptClient.getConversation(token, new GPTRequest(list.toString().substring(2), answer));
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> choice = choices.get(0);

        Map<String, Object> message = (Map<String, Object>) choice.get("message");
        String content = (String) message.get("content");

        System.out.println("content = " + content);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();

        // items 문자열을 JSON 객체로 변환하여 JSON 응답에 추가
        ObjectNode itemsNode = (ObjectNode) mapper.readTree(content);
        StringBuilder trueItems = new StringBuilder();

        itemsNode.fieldNames().forEachRemaining(fieldName -> {
            JsonNode value = itemsNode.get(fieldName);
            jsonResponse.set(fieldName, value);
            if (!fieldName.equals("weather")) {
                if (value.asBoolean()) {
                    if (trueItems.length() > 0) {
                        trueItems.append(", ");
                    }
                    System.out.println(fieldName);
                    trueItems.append(fieldName);
                }
            }
        });

        String item = trueItems.toString();

        // true인 항목을 문자열로 출력 (weather는 제외)
        System.out.println("True items: " + item);

        mqttGateway.sendToMqtt(trueItems.toString());
        mqttGateway.sendToMqtt(mot);

        Saave saave = saaveRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        saave.update(jsonResponse.toString(), "1");

        return jsonResponse;
    }
}
