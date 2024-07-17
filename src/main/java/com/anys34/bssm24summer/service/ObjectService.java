package com.anys34.bssm24summer.service;

import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.feign.GPTClient;
import com.anys34.bssm24summer.feign.WeatherInformationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ObjectService {
    private final WeatherInformationClient weatherInformationClient;
    private final GPTClient gptClient;

    @Value("${gpt.token}")
    private String token;

    public WeatherResponse weather() {
        Map<String, Object> response = weatherInformationClient
                .gerWeatherInformation();

        List<Map<String, Object>> weatherList = (List<Map<String,Object>>) response.get("weather");
        Map<String, Object> weathers = weatherList.get(0);
        Map<String, Object> main = (Map<String, Object>) response.get("main");

        String weather = (String) weathers.get("description");
        double temp = (Double) main.get("temp");
        return new WeatherResponse(weather, temp);
    }

    public void chat() {
        gptClient.getConversation(token)
    }
}
