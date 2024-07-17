package com.anys34.bssm24summer.controller;

import com.anys34.bssm24summer.controller.dto.ListResponse;
import com.anys34.bssm24summer.controller.dto.WeatherResponse;
import com.anys34.bssm24summer.service.ObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ObjectController {
    private final ObjectService objectService;

    @GetMapping("/list")
    public ListResponse list() {
        return new ListResponse(2, true, false);
    }

    @GetMapping("/weather")
    public WeatherResponse weather() {
        return objectService.weather();
    }

    @PostMapping("/chat")
    public void chat() {
        return
    }
}
