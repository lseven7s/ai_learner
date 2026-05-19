package com.ai.learning.dto;

import lombok.Data;

@Data
public class StudyMaterialQueryDTO {
    
    private Integer pageNum = 1;
    
    private Integer pageSize = 10;
    
    private String title;
    
    private String fileType;
    
    private String category;
    
    private String tags;
    
    private Integer status;
}
