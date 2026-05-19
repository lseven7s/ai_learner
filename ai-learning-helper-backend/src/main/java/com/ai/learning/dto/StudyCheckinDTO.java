package com.ai.learning.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudyCheckinDTO {
    private Long id;

    private Long planId;

    private LocalDate checkinDate;

    private LocalDateTime checkinTime;

    private Integer studyDuration;

    @JsonAlias("content")
    private String studyContent;

    private String mood;
}
