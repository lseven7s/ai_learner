package com.ai.learning.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudyCheckinDTO {
    private Long id;

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    private Long planId;

    @NotNull(message = "打卡日期不能为空")
    private LocalDate checkinDate;

    private LocalDateTime checkinTime;

    private Integer studyDuration;

    private String studyContent;

    private String mood;
}
