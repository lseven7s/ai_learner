package com.ai.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ai.learning.dto.StudyPlanCreateDTO;
import com.ai.learning.dto.StudyPlanGenerateDTO;
import com.ai.learning.dto.StudyPlanUpdateDTO;
import com.ai.learning.entity.StudyPlan;
import com.ai.learning.exception.BusinessException;
import com.ai.learning.mapper.StudyPlanMapper;
import com.ai.learning.service.AiModelService;
import com.ai.learning.service.StudyPlanService;
import com.ai.learning.vo.StudyPlanVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyPlanServiceImpl implements StudyPlanService {

    private final StudyPlanMapper studyPlanMapper;
    private final AiModelService aiModelService;

    @Override
    public Long createStudyPlan(Long userId, StudyPlanCreateDTO dto) {
        StudyPlan studyPlan = new StudyPlan();
        BeanUtil.copyProperties(dto, studyPlan);
        studyPlan.setUserId(userId);
        studyPlan.setProgress(BigDecimal.ZERO);
        studyPlan.setStatus(1);
        studyPlanMapper.insert(studyPlan);
        return studyPlan.getId();
    }

    @Override
    public List<StudyPlanVO> getStudyPlanList(Long userId) {
        LambdaQueryWrapper<StudyPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyPlan::getUserId, userId);
        wrapper.orderByDesc(StudyPlan::getCreateTime);
        List<StudyPlan> studyPlanList = studyPlanMapper.selectList(wrapper);
        return studyPlanList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public StudyPlanVO getStudyPlanDetail(Long userId, Long planId) {
        StudyPlan studyPlan = getStudyPlanByIdAndUserId(userId, planId);
        return convertToVO(studyPlan);
    }

    @Override
    public void updateStudyPlan(Long userId, StudyPlanUpdateDTO dto) {
        StudyPlan studyPlan = getStudyPlanByIdAndUserId(userId, dto.getId());
        BeanUtil.copyProperties(dto, studyPlan, "id", "userId", "createTime");
        studyPlanMapper.updateById(studyPlan);
    }

    @Override
    public void deleteStudyPlan(Long userId, Long planId) {
        StudyPlan studyPlan = getStudyPlanByIdAndUserId(userId, planId);
        studyPlanMapper.deleteById(planId);
    }

    @Override
    public StudyPlanVO generateStudyPlan(Long userId, StudyPlanGenerateDTO dto) {
        StudyPlan studyPlan = new StudyPlan();
        studyPlan.setUserId(userId);
        studyPlan.setTitle(dto.getGoal() + "学习计划");
        studyPlan.setDescription("基于AI生成的学习计划：" + dto.getGoal());
        studyPlan.setStartDate(LocalDate.now());
        studyPlan.setEndDate(LocalDate.now().plusDays(30));
        studyPlan.setDailyGoal("每天学习2小时");
        
        String aiGeneratedContent = aiModelService.generateStudyPlanContent(dto);
        studyPlan.setDescription(studyPlan.getDescription() + "\n\n" + aiGeneratedContent);
        
        studyPlan.setProgress(BigDecimal.ZERO);
        studyPlan.setStatus(1);
        studyPlanMapper.insert(studyPlan);
        return convertToVO(studyPlan);
    }

    private StudyPlan getStudyPlanByIdAndUserId(Long userId, Long planId) {
        LambdaQueryWrapper<StudyPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyPlan::getId, planId);
        wrapper.eq(StudyPlan::getUserId, userId);
        StudyPlan studyPlan = studyPlanMapper.selectOne(wrapper);
        if (studyPlan == null) {
            throw new BusinessException("学习计划不存在");
        }
        return studyPlan;
    }

    private StudyPlanVO convertToVO(StudyPlan studyPlan) {
        StudyPlanVO vo = new StudyPlanVO();
        BeanUtil.copyProperties(studyPlan, vo);
        return vo;
    }
}
