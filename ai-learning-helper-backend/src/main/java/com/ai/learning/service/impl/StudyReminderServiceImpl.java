package com.ai.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ai.learning.dto.StudyReminderDTO;
import com.ai.learning.entity.StudyReminder;
import com.ai.learning.mapper.StudyReminderMapper;
import com.ai.learning.service.StudyReminderService;
import com.ai.learning.vo.StudyReminderVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyReminderServiceImpl implements StudyReminderService {

    @Autowired
    private StudyReminderMapper studyReminderMapper;

    @Override
    public StudyReminderVO create(StudyReminderDTO dto) {
        StudyReminder reminder = BeanUtil.copyProperties(dto, StudyReminder.class);
        reminder.setIsSent(0);
        reminder.setStatus(1);
        studyReminderMapper.insert(reminder);
        return BeanUtil.copyProperties(reminder, StudyReminderVO.class);
    }

    @Override
    public StudyReminderVO update(StudyReminderDTO dto) {
        StudyReminder reminder = BeanUtil.copyProperties(dto, StudyReminder.class);
        studyReminderMapper.updateById(reminder);
        return BeanUtil.copyProperties(studyReminderMapper.selectById(dto.getId()), StudyReminderVO.class);
    }

    @Override
    public void delete(Long id) {
        studyReminderMapper.deleteById(id);
    }

    @Override
    public StudyReminderVO getById(Long id) {
        StudyReminder reminder = studyReminderMapper.selectById(id);
        return BeanUtil.copyProperties(reminder, StudyReminderVO.class);
    }

    @Override
    public List<StudyReminderVO> getByUserId(Long userId) {
        LambdaQueryWrapper<StudyReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyReminder::getUserId, userId);
        wrapper.orderByDesc(StudyReminder::getReminderTime);
        List<StudyReminder> list = studyReminderMapper.selectList(wrapper);
        return list.stream()
                .map(item -> BeanUtil.copyProperties(item, StudyReminderVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudyReminderVO> getPendingReminders() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<StudyReminder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyReminder::getIsSent, 0);
        wrapper.eq(StudyReminder::getStatus, 1);
        wrapper.le(StudyReminder::getReminderTime, now);
        List<StudyReminder> list = studyReminderMapper.selectList(wrapper);
        return list.stream()
                .map(item -> BeanUtil.copyProperties(item, StudyReminderVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void markAsSent(Long id) {
        StudyReminder reminder = new StudyReminder();
        reminder.setId(id);
        reminder.setIsSent(1);
        studyReminderMapper.updateById(reminder);
    }
}
