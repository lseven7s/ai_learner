package com.ai.learning.controller;

import com.ai.learning.common.Result;
import com.ai.learning.dto.StudyCheckinDTO;
import com.ai.learning.service.StudyCheckinService;
import com.ai.learning.vo.StudyCheckinVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-checkins")
@RequiredArgsConstructor
public class StudyCheckinController {

    private final StudyCheckinService studyCheckinService;

    @PostMapping
    public Result<StudyCheckinVO> create(@Valid @RequestBody StudyCheckinDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        StudyCheckinVO vo = studyCheckinService.create(userId, dto);
        return Result.success(vo);
    }

    @PutMapping
    public Result<StudyCheckinVO> update(@Valid @RequestBody StudyCheckinDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        StudyCheckinVO vo = studyCheckinService.update(userId, dto);
        return Result.success(vo);
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        studyCheckinService.delete(userId, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<StudyCheckinVO> getById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        StudyCheckinVO vo = studyCheckinService.getById(userId, id);
        return Result.success(vo);
    }

    @GetMapping("/me")
    public Result<List<StudyCheckinVO>> getMyCheckins(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<StudyCheckinVO> list = studyCheckinService.getByUserId(userId);
        return Result.success(list);
    }

    @GetMapping("/plan/{planId}")
    public Result<List<StudyCheckinVO>> getByPlanId(@PathVariable Long planId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<StudyCheckinVO> list = studyCheckinService.getByPlanId(userId, planId);
        return Result.success(list);
    }
}
