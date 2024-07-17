package com.anys34.bssm24summer.controller;

import com.anys34.bssm24summer.controller.dto.ListResponse;
import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.service.MqttService;
import com.anys34.bssm24summer.service.ObjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ObjectController {
    private final ObjectService objectService;
    private final MqttService.MqttGateway mqttGateway;

    @GetMapping("/list")
    public ListResponse list() {
        return new ListResponse(2, true, false);
    }

    @GetMapping("/weather")
    public WeatherResponse weather() {
        return objectService.weather();
    }

    @GetMapping("/chat")
    public void chat(@RequestParam String answer) {
        objectService.chat(answer);
    }

    @GetMapping("/send")
    public void send(@RequestParam String message) {
        mqttGateway.sendToMqtt(message);
    }
}
