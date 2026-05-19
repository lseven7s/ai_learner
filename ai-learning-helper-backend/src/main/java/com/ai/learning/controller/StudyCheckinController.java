package com.ai.learning.controller;

import com.ai.learning.common.Result;
import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.service.StudyCheckinService;
import com.ai.learning.vo.StudyCheckinVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-checkins")
public class StudyCheckinController {

    @Autowired
    private StudyCheckinService studyCheckinService;

    @PostMapping
    public Result<StudyCheckinVO> create(@Valid @RequestBody StudyCheckinDTO dto) {
        StudyCheckinVO vo = studyCheckinService.create(dto);
        return Result.success(vo);
    }

    @PutMapping
    public Result<StudyCheckinVO> update(@Valid @RequestBody StudyCheckinDTO dto) {
        StudyCheckinVO vo = studyCheckinService.update(dto);
        return Result.success(vo);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studyCheckinService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<StudyCheckinVO> getById(@PathVariable Long id) {
        StudyCheckinVO vo = studyCheckinService.getById(id);
        return Result.success(vo);
    }

    @GetMapping("/user/{userId}")
    public Result<List<StudyCheckinVO>> getByUserId(@PathVariable Long userId) {
        List<StudyCheckinVO> list = studyCheckinService.getByUserId(userId);
        return Result.success(list);
    }

    @GetMapping("/plan/{planId}")
    public Result<List<StudyCheckinVO>> getByPlanId(@PathVariable Long planId) {
        List<StudyCheckinVO> list = studyCheckinService.getByPlanId(planId);
        return Result.success(list);
    }
}
