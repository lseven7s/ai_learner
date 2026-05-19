package com.ai.learning.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudyMaterialUploadDTO {
    
    @NotBlank(message = "标题不能为空")
    private String title;
    
    private String description;
    
    private String content;
    
    private String fileUrl;
    
    private String fileType;
    
    private String category;
    
    private String tags;
    
    private Integer status;
}
