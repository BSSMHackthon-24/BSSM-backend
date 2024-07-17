package com.anys34.bssm24summer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "WeatherInformationClient", url = "https://api.openweathermap.org/data/2.5/weather")
public interface WeatherInformationClient {
    @GetMapping("?lang=kr&units=metric&lat=35.188720426507594&lon=128.90350627386832&appid=04730d725e085a84657f794cc48951b7")
    Map<String, Object> gerWeatherInformation();
}
