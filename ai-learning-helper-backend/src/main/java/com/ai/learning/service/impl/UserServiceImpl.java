package com.ai.learning.service.impl;

import com.ai.learning.dto.UserLoginDTO;
import com.ai.learning.dto.UserRegisterDTO;
import com.ai.learning.dto.UserUpdateDTO;
import com.ai.learning.entity.User;
import com.ai.learning.exception.BusinessException;
import com.ai.learning.mapper.UserMapper;
import com.ai.learning.service.UserService;
import com.ai.learning.utils.JwtUtil;
import com.ai.learning.vo.LoginResponseVO;
import com.ai.learning.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致");
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userRegisterDTO.getUsername());
        if (userMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }

        if (userRegisterDTO.getEmail() != null && !userRegisterDTO.getEmail().isBlank()) {
            LambdaQueryWrapper<User> emailWrapper = new LambdaQueryWrapper<>();
            emailWrapper.eq(User::getEmail, userRegisterDTO.getEmail());
            if (userMapper.selectCount(emailWrapper) > 0) {
                throw new BusinessException("邮箱已被注册");
            }
        }

        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setNickname(userRegisterDTO.getNickname() != null ? userRegisterDTO.getNickname() : userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPhone(userRegisterDTO.getPhone());
        user.setStatus(1);

        userMapper.insert(user);
    }

    @Override
    public LoginResponseVO login(UserLoginDTO userLoginDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userLoginDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!Integer.valueOf(1).equals(user.getStatus())) {
            throw new BusinessException("账号已被禁用");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        UserVO userVO = convertToUserVO(user);

        return LoginResponseVO.builder()
                .token(token)
                .user(userVO)
                .build();
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToUserVO(user);
    }

    @Override
    public UserVO updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (userUpdateDTO.getNickname() != null) {
            user.setNickname(userUpdateDTO.getNickname());
        }
        if (userUpdateDTO.getAvatar() != null) {
            user.setAvatar(userUpdateDTO.getAvatar());
        }
        if (userUpdateDTO.getEmail() != null) {
            LambdaQueryWrapper<User> emailWrapper = new LambdaQueryWrapper<>();
            emailWrapper.eq(User::getEmail, userUpdateDTO.getEmail());
            emailWrapper.ne(User::getId, userId);
            if (userMapper.selectCount(emailWrapper) > 0) {
                throw new BusinessException("邮箱已被使用");
            }
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPhone() != null) {
            user.setPhone(userUpdateDTO.getPhone());
        }

        userMapper.updateById(user);
        return convertToUserVO(user);
    }

    private UserVO convertToUserVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .status(user.getStatus())
                .createTime(user.getCreateTime())
                .build();
    }
}
