package com.ai.learning.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudyReminderVO {
    private Long id;
    private Long userId;
    private Long planId;
    private String title;
    private String content;
    private LocalDateTime reminderTime;
    private Integer repeatType;
    private Integer isSent;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
