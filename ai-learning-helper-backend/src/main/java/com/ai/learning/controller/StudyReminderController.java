package com.ai.learning.controller;

import com.ai.learning.common.Result;
import com.ai.learning.dto.StudyReminderDTO;
import com.ai.learning.service.StudyReminderService;
import com.ai.learning.vo.StudyReminderVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-reminders")
public class StudyReminderController {

    @Autowired
    private StudyReminderService studyReminderService;

    @PostMapping
    public Result<StudyReminderVO> create(@Valid @RequestBody StudyReminderDTO dto) {
        StudyReminderVO vo = studyReminderService.create(dto);
        return Result.success(vo);
    }

    @PutMapping
    public Result<StudyReminderVO> update(@Valid @RequestBody StudyReminderDTO dto) {
        StudyReminderVO vo = studyReminderService.update(dto);
        return Result.success(vo);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studyReminderService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<StudyReminderVO> getById(@PathVariable Long id) {
        StudyReminderVO vo = studyReminderService.getById(id);
        return Result.success(vo);
    }

    @GetMapping("/user/{userId}")
    public Result<List<StudyReminderVO>> getByUserId(@PathVariable Long userId) {
        List<StudyReminderVO> list = studyReminderService.getByUserId(userId);
        return Result.success(list);
    }
}
