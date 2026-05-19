package com.ai.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.entity.StudyCheckin;
import com.ai.learning.mapper.StudyCheckinMapper;
import com.ai.learning.service.StudyCheckinService;
import com.ai.learning.vo.StudyCheckinVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyCheckinServiceImpl implements StudyCheckinService {

    @Autowired
    private StudyCheckinMapper studyCheckinMapper;

    @Override
    public StudyCheckinVO create(StudyCheckinDTO dto) {
        StudyCheckin checkin = BeanUtil.copyProperties(dto, StudyCheckin.class);
        if (checkin.getCheckinDate() == null) {
            checkin.setCheckinDate(LocalDate.now());
        }
        if (checkin.getCheckinTime() == null) {
            checkin.setCheckinTime(LocalDateTime.now());
        }
        studyCheckinMapper.insert(checkin);
        return BeanUtil.copyProperties(checkin, StudyCheckinVO.class);
    }

    @Override
    public StudyCheckinVO update(StudyCheckinDTO dto) {
        StudyCheckin checkin = BeanUtil.copyProperties(dto, StudyCheckin.class);
        studyCheckinMapper.updateById(checkin);
        return BeanUtil.copyProperties(studyCheckinMapper.selectById(dto.getId()), StudyCheckinVO.class);
    }

    @Override
    public void delete(Long id) {
        studyCheckinMapper.deleteById(id);
    }

    @Override
    public StudyCheckinVO getById(Long id) {
        StudyCheckin checkin = studyCheckinMapper.selectById(id);
        return BeanUtil.copyProperties(checkin, StudyCheckinVO.class);
    }

    @Override
    public List<StudyCheckinVO> getByUserId(Long userId) {
        LambdaQueryWrapper<StudyCheckin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyCheckin::getUserId, userId);
        wrapper.orderByDesc(StudyCheckin::getCheckinDate);
        List<StudyCheckin> list = studyCheckinMapper.selectList(wrapper);
        return list.stream()
                .map(item -> BeanUtil.copyProperties(item, StudyCheckinVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyCheckinVO> getByPlanId(Long planId) {
        LambdaQueryWrapper<StudyCheckin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyCheckin::getPlanId, planId);
        wrapper.orderByDesc(StudyCheckin::getCheckinDate);
        List<StudyCheckin> list = studyCheckinMapper.selectList(wrapper);
        return list.stream()
                .map(item -> BeanUtil.copyProperties(item, StudyCheckinVO.class))
                .collect(Collectors.toList());
    }
}
