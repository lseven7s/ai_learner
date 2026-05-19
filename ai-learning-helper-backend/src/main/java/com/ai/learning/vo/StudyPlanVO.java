package com.ai.learning.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class StudyPlanVO {
    private Long id;

    private Long userId;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String dailyGoal;

    private BigDecimal progress;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
