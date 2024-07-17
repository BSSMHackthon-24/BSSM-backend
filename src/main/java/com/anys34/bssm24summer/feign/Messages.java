package com.anys34.bssm24summer.feign;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Messages {
    private String role;
    private String content;

    public Messages(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
