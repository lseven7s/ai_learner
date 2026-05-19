package com.ai.learning.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    
    @Size(max = 50, message = "昵称长度不能超过50")
    private String nickname;
    
    private String avatar;
    
    private String email;
    
    private String phone;
}
