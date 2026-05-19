package com.ai.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.entity.StudyCheckin;
import com.ai.learning.exception.BusinessException;
import com.ai.learning.mapper.StudyCheckinMapper;
import com.ai.learning.service.StudyCheckinService;
import com.ai.learning.vo.StudyCheckinVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyCheckinServiceImpl implements StudyCheckinService {

    private final StudyCheckinMapper studyCheckinMapper;

    @Override
    public StudyCheckinVO create(Long userId, StudyCheckinDTO dto) {
        LocalDate checkinDate = dto.getCheckinDate() != null ? dto.getCheckinDate() : LocalDate.now();

        LambdaQueryWrapper<StudyCheckin> duplicateWrapper = new LambdaQueryWrapper<>();
        duplicateWrapper.eq(StudyCheckin::getUserId, userId);
        duplicateWrapper.eq(StudyCheckin::getCheckinDate, checkinDate);
        if (studyCheckinMapper.selectCount(duplicateWrapper) > 0) {
            throw new BusinessException("今日已打卡");
        }

        StudyCheckin checkin = BeanUtil.copyProperties(dto, StudyCheckin.class);
        checkin.setUserId(userId);
        checkin.setCheckinDate(checkinDate);
        if (checkin.getCheckinTime() == null) {
            checkin.setCheckinTime(LocalDateTime.now());
        }
        studyCheckinMapper.insert(checkin);
        return BeanUtil.copyProperties(checkin, StudyCheckinVO.class);
    }

    @Override
    public StudyCheckinVO update(Long userId, StudyCheckinDTO dto) {
        StudyCheckin existing = getOwnedCheckin(userId, dto.getId());
        BeanUtil.copyProperties(dto, existing, "id", "userId", "createTime");
        studyCheckinMapper.updateById(existing);
        return BeanUtil.copyProperties(existing, StudyCheckinVO.class);
    }

    @Override
    public void delete(Long userId, Long id) {
        getOwnedCheckin(userId, id);
        studyCheckinMapper.deleteById(id);
    }

    @Override
    public StudyCheckinVO getById(Long userId, Long id) {
        StudyCheckin checkin = getOwnedCheckin(userId, id);
        return BeanUtil.copyProperties(checkin, StudyCheckinVO.class);
    }

    @Override
    public List<StudyCheckinVO> getByUserId(Long userId) {
        LambdaQueryWrapper<StudyCheckin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyCheckin::getUserId, userId);
        wrapper.orderByDesc(StudyCheckin::getCheckinDate);
        return studyCheckinMapper.selectList(wrapper).stream()
                .map(item -> BeanUtil.copyProperties(item, StudyCheckinVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyCheckinVO> getByPlanId(Long userId, Long planId) {
        LambdaQueryWrapper<StudyCheckin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyCheckin::getUserId, userId);
        wrapper.eq(StudyCheckin::getPlanId, planId);
        wrapper.orderByDesc(StudyCheckin::getCheckinDate);
        return studyCheckinMapper.selectList(wrapper).stream()
                .map(item -> BeanUtil.copyProperties(item, StudyCheckinVO.class))
                .collect(Collectors.toList());
    }

    private StudyCheckin getOwnedCheckin(Long userId, Long id) {
        StudyCheckin checkin = studyCheckinMapper.selectById(id);
        if (checkin == null) {
            throw new BusinessException("打卡记录不存在");
        }
        if (!userId.equals(checkin.getUserId())) {
            throw new BusinessException("无权操作该打卡记录");
        }
        return checkin;
    }
}
