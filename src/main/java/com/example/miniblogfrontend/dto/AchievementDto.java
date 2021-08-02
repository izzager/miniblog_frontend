package com.example.miniblogfrontend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AchievementDto {

    private Long userId;

    private String achievementName;

    private String description;
}
