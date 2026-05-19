package com.ai.learning.service;

import com.ai.learning.dto.StudyReminderDTO;
import com.ai.learning.vo.StudyReminderVO;
import java.util.List;

public interface StudyReminderService {
    StudyReminderVO create(StudyReminderDTO dto);

    StudyReminderVO update(StudyReminderDTO dto);

    void delete(Long id);

    StudyReminderVO getById(Long id);

    List<StudyReminderVO> getByUserId(Long userId);

    List<StudyReminderVO> getPendingReminders();

    void markAsSent(Long id);
}
