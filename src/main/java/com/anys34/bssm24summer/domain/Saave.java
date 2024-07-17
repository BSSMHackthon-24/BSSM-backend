package com.anys34.bssm24summer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Saave {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String save;

    private String weather;

    public void update(String save, String weather) {
        this.save = save;
        this.weather = weather;
    }
}
