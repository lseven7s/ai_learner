package com.ai.learning.service;

import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.vo.StudyCheckinVO;
import java.util.List;

public interface StudyCheckinService {
    StudyCheckinVO create(StudyCheckinDTO dto);

    StudyCheckinVO update(StudyCheckinDTO dto);

    void delete(Long id);

    StudyCheckinVO getById(Long id);

    List<StudyCheckinVO> getByUserId(Long userId);

    List<StudyCheckinVO> getByPlanId(Long planId);
}
