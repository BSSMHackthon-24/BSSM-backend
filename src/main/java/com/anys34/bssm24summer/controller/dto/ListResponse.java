package com.anys34.bssm24summer.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ListResponse {
    private int weather;
    private boolean umb;
    private boolean mask;

    public ListResponse(int weather, boolean umb, boolean mask) {
        this.weather = weather;
        this.umb = umb;
        this.mask = mask;
    }
}
