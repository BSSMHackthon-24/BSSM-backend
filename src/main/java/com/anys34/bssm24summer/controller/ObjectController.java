package com.anys34.bssm24summer.controller;

import com.anys34.bssm24summer.controller.dto.ListResponse;
import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.domain.Saave;
import com.anys34.bssm24summer.domain.repository.SaaveRepository;
import com.anys34.bssm24summer.service.MqttService;
import com.anys34.bssm24summer.service.ObjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequiredArgsConstructor
@RestController
public class ObjectController {
    private final ObjectService objectService;
    private final MqttService.MqttGateway mqttGateway;
    private final SaaveRepository saaveRepository;

    @GetMapping("/list")
    public ObjectNode list() throws JsonProcessingException {
        Saave save = saaveRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonResponse = mapper.createObjectNode();

        // weather를 int로 변환하여 JSON에 추가
        jsonResponse.put("weather", Integer.parseInt(save.getWeather()));

        // items 문자열을 JSON 객체로 변환하여 JSON 응답에 추가
        ObjectNode itemsNode = (ObjectNode) mapper.readTree(save.getSave());
        itemsNode.fieldNames().forEachRemaining(fieldName -> {
            jsonResponse.set(fieldName, itemsNode.get(fieldName));
        });
        return jsonResponse;
    }

    @GetMapping("/weather")
    public WeatherResponse weather() {
        return objectService.weather();
    }

    @GetMapping("/chat")
    public List<String> chat(@RequestParam String answer) throws JsonProcessingException {
        return objectService.chat(answer);
    }

    @GetMapping("/send")
    public void send(@RequestParam String message) {
        mqttGateway.sendToMqtt(message);
    }
}
