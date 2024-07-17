package com.anys34.bssm24summer.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherResponse {
    private String weather;
    private Double temp;

    public WeatherResponse(String weather, Double temp) {
        this.weather = weather;
        this.temp = temp;
    }
}
