package com.anys34.bssm24summer.feign;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class GPTRequest {
    private final String model = "gpt-4";
    private List<Messages> messages;

    public GPTRequest(String objects) {
        List<Messages> data = new ArrayList<>();
        data.add(new Messages("system", "You are a helpful assistant."));
        data.add(new Messages("system", "You will recommend necessary items based on the user's situation."));
        data.add(new Messages("system", "The items to consider are: " + objects));
        data.add(new Messages("system", "You are a helpful assistant."));
    }
}
