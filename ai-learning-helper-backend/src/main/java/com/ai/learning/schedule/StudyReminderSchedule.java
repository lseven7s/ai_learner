package com.ai.learning.schedule;

import com.ai.learning.service.StudyReminderService;
import com.ai.learning.vo.StudyReminderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StudyReminderSchedule {

    @Autowired
    private StudyReminderService studyReminderService;

    @Scheduled(fixedRate = 60000)
    public void sendStudyReminders() {
        log.info("开始检查并推送学习提醒...");
        List<StudyReminderVO> pendingReminders = studyReminderService.getPendingReminders();
        
        for (StudyReminderVO reminder : pendingReminders) {
            try {
                pushReminder(reminder);
                studyReminderService.markAsSent(reminder.getId());
                log.info("成功推送学习提醒: id={}, title={}", reminder.getId(), reminder.getTitle());
            } catch (Exception e) {
                log.error("推送学习提醒失败: id={}", reminder.getId(), e);
            }
        }
    }

    private void pushReminder(StudyReminderVO reminder) {
        log.info("向用户 {} 推送学习提醒: [{}] - {}", 
                reminder.getUserId(), reminder.getTitle(), reminder.getContent());
    }
}
