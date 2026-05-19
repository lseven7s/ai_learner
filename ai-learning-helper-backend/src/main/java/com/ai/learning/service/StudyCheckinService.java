package com.ai.learning.service;

import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.vo.StudyCheckinVO;

import java.util.List;

public interface StudyCheckinService {
    StudyCheckinVO create(Long userId, StudyCheckinDTO dto);

    StudyCheckinVO update(Long userId, StudyCheckinDTO dto);

    void delete(Long userId, Long id);

    StudyCheckinVO getById(Long userId, Long id);

    List<StudyCheckinVO> getByUserId(Long userId);

    List<StudyCheckinVO> getByPlanId(Long userId, Long planId);
}
