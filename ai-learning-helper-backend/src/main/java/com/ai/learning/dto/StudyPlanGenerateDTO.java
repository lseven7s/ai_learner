package com.ai.learning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudyPlanGenerateDTO {
    @NotBlank(message = "学习目标不能为空")
    private String goal;

    @NotBlank(message = "学习时长不能为空")
    private String duration;

    private String level;

    private String preferences;
}
