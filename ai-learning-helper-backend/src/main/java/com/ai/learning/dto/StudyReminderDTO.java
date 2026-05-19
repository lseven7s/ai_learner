package com.ai.learning.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class StudyReminderDTO {
    private Long id;

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    private Long planId;

    @NotBlank(message = "标题不能为空")
    private String title;

    private String content;

    @NotNull(message = "提醒时间不能为空")
    private LocalDateTime reminderTime;

    @NotNull(message = "重复类型不能为空")
    private Integer repeatType;
}
