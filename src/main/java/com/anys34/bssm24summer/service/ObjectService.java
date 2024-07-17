package com.anys34.bssm24summer.service;

import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.domain.Objects;
import com.anys34.bssm24summer.domain.repository.ObjectRepository;
import com.anys34.bssm24summer.feign.GPTClient;
import com.anys34.bssm24summer.feign.GPTRequest;
import com.anys34.bssm24summer.feign.WeatherInformationClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ObjectService {
    private final WeatherInformationClient weatherInformationClient;
    private final GPTClient gptClient;
    private final ObjectRepository objectRepository;
    private final MqttService.MqttGateway mqttGateway;

    @Value("${gpt.token}")
    private String token;

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

    public List<String> chat(String answer) throws JsonProcessingException {
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

        String[] split = content.split("\n");
        System.out.println("content = " + content);
        System.out.println("split[0] = " + split[0]);
        System.out.println("split[1] = " + split[1]);
        mqttGateway.sendToMqtt(split[1]);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(split[0]);

        List<String> trueItems = new ArrayList<>();
        Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (field.getValue().asBoolean()) {
                trueItems.add(field.getKey());
            }
        }

        return trueItems;
    }
}
