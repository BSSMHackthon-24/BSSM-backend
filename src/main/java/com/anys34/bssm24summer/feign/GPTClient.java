package com.anys34.bssm24summer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "GPTClient", url = "https://api.openai.com/v1/chat/completions")
public interface GPTClient {
    @PostMapping
    Map<String, Object> getConversation(
            @RequestHeader("Authorization") String token
            @RequestBody
    );
}
