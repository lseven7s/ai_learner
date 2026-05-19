package com.ai.learning.controller;

import com.ai.learning.common.Result;
import com.ai.learning.dto.UserLoginDTO;
import com.ai.learning.dto.UserRegisterDTO;
import com.ai.learning.dto.UserUpdateDTO;
import com.ai.learning.service.UserService;
import com.ai.learning.vo.LoginResponseVO;
import com.ai.learning.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<LoginResponseVO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        LoginResponseVO loginResponseVO = userService.login(userLoginDTO);
        return Result.success(loginResponseVO);
    }

    @GetMapping("/info")
    public Result<UserVO> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }

    @PutMapping("/info")
    public Result<UserVO> updateUserInfo(HttpServletRequest request, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        Long userId = (Long) request.getAttribute("userId");
        UserVO userVO = userService.updateUserInfo(userId, userUpdateDTO);
        return Result.success(userVO);
    }
}
