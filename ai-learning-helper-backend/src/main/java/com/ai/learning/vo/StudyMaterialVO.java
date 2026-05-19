package com.ai.learning.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudyMaterialVO {
    
    private Long id;
    
    private Long userId;
    
    private String title;
    
    private String description;
    
    private String content;
    
    private String fileUrl;
    
    private String fileType;
    
    private String category;
    
    private String tags;
    
    private Integer status;
    
    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;
}
