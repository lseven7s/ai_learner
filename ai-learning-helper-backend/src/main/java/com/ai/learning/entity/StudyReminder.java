package com.ai.learning.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("study_reminder")
public class StudyReminder {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long planId;
    
    private String title;
    
    private String content;
    
    private LocalDateTime reminderTime;
    
    private Integer repeatType;
    
    private Integer isSent;
    
    private Integer status;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}
