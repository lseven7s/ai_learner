package com.ai.learning.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("study_checkin")
public class StudyCheckin {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long userId;
    
    private Long planId;
    
    private LocalDate checkinDate;
    
    private LocalDateTime checkinTime;
    
    private Integer studyDuration;
    
    private String studyContent;
    
    private String mood;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Integer deleted;
}
