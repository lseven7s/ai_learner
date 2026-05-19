package com.ai.learning.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class StudyPlanUpdateDTO {
    @NotNull(message = "计划ID不能为空")
    private Long id;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String dailyGoal;

    private BigDecimal progress;

    private Integer status;
}
