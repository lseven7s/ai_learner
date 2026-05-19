package com.ai.learning.service;

import com.ai.learning.dto.StudyPlanCreateDTO;
import com.ai.learning.dto.StudyPlanGenerateDTO;
import com.ai.learning.dto.StudyPlanUpdateDTO;
import com.ai.learning.vo.StudyPlanVO;
import java.util.List;

public interface StudyPlanService {
    Long createStudyPlan(Long userId, StudyPlanCreateDTO dto);

    List<StudyPlanVO> getStudyPlanList(Long userId);

    StudyPlanVO getStudyPlanDetail(Long userId, Long planId);

    void updateStudyPlan(Long userId, StudyPlanUpdateDTO dto);

    void deleteStudyPlan(Long userId, Long planId);

    StudyPlanVO generateStudyPlan(Long userId, StudyPlanGenerateDTO dto);
}
