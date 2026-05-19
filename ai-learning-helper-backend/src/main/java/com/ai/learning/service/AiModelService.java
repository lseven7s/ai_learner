package com.ai.learning.service;

import com.ai.learning.dto.StudyPlanGenerateDTO;

public interface AiModelService {
    String generateStudyPlanContent(StudyPlanGenerateDTO dto);
}
