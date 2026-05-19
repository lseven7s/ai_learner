package com.ai.learning.service;

import com.ai.learning.dto.UserLoginDTO;
import com.ai.learning.dto.UserRegisterDTO;
import com.ai.learning.dto.UserUpdateDTO;
import com.ai.learning.vo.LoginResponseVO;
import com.ai.learning.vo.UserVO;

public interface UserService {
    
    void register(UserRegisterDTO userRegisterDTO);
    
    LoginResponseVO login(UserLoginDTO userLoginDTO);
    
    UserVO getUserInfo(Long userId);
    
    UserVO updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO);
}
