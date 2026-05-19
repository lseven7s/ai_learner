package com.ai.learning.controller;

import com.ai.learning.common.Result;
import com.ai.learning.dto.StudyPlanCreateDTO;
import com.ai.learning.dto.StudyPlanGenerateDTO;
import com.ai.learning.dto.StudyPlanUpdateDTO;
import com.ai.learning.service.StudyPlanService;
import com.ai.learning.vo.StudyPlanVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/study-plans")
@RequiredArgsConstructor
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    @PostMapping
    public Result<Long> createStudyPlan(@Valid @RequestBody StudyPlanCreateDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Long planId = studyPlanService.createStudyPlan(userId, dto);
        return Result.success(planId);
    }

    @GetMapping
    public Result<List<StudyPlanVO>> getStudyPlanList(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<StudyPlanVO> list = studyPlanService.getStudyPlanList(userId);
        return Result.success(list);
    }

    @GetMapping("/{planId}")
    public Result<StudyPlanVO> getStudyPlanDetail(@PathVariable Long planId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        StudyPlanVO vo = studyPlanService.getStudyPlanDetail(userId, planId);
        return Result.success(vo);
    }

    @PutMapping
    public Result<Void> updateStudyPlan(@Valid @RequestBody StudyPlanUpdateDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        studyPlanService.updateStudyPlan(userId, dto);
        return Result.success();
    }

    @DeleteMapping("/{planId}")
    public Result<Void> deleteStudyPlan(@PathVariable Long planId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        studyPlanService.deleteStudyPlan(userId, planId);
        return Result.success();
    }

    @PostMapping("/generate")
    public Result<StudyPlanVO> generateStudyPlan(@Valid @RequestBody StudyPlanGenerateDTO dto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        StudyPlanVO vo = studyPlanService.generateStudyPlan(userId, dto);
        return Result.success(vo);
    }
}
