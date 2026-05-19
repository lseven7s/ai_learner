package com.ai.learning.vo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudyCheckinVO {
    private Long id;
    private Long userId;
    private Long planId;
    private LocalDate checkinDate;
    private LocalDateTime checkinTime;
    private Integer studyDuration;
    private String studyContent;
    private String mood;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
