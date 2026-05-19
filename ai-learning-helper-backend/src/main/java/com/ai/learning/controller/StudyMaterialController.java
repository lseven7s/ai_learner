package com.ai.learning.controller;

import cn.hutool.core.util.IdUtil;
import com.ai.learning.common.Result;
import com.ai.learning.dto.StudyMaterialQueryDTO;
import com.ai.learning.dto.StudyMaterialUploadDTO;
import com.ai.learning.service.StudyMaterialService;
import com.ai.learning.utils.MinioUtil;
import com.ai.learning.vo.StudyMaterialVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/study-material")
@RequiredArgsConstructor
public class StudyMaterialController {
    
    private final StudyMaterialService studyMaterialService;
    
    private final MinioUtil minioUtil;
    
    @PostMapping("/upload")
    public Result<Long> uploadMaterial(HttpServletRequest request, @Valid @RequestBody StudyMaterialUploadDTO uploadDTO) {
        Long userId = (Long) request.getAttribute("userId");
        Long materialId = studyMaterialService.uploadMaterial(userId, uploadDTO);
        return Result.success("上传成功", materialId);
    }
    
    @GetMapping("/list")
    public Result<IPage<StudyMaterialVO>> queryMaterialList(HttpServletRequest request, StudyMaterialQueryDTO queryDTO) {
        Long userId = (Long) request.getAttribute("userId");
        IPage<StudyMaterialVO> page = studyMaterialService.queryMaterialList(userId, queryDTO);
        return Result.success(page);
    }
    
    @GetMapping("/{id}")
    public Result<StudyMaterialVO> getMaterialById(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        StudyMaterialVO studyMaterialVO = studyMaterialService.getMaterialById(userId, id);
        return Result.success(studyMaterialVO);
    }
    
    @DeleteMapping("/{id}")
    public Result<Void> deleteMaterial(HttpServletRequest request, @PathVariable Long id) {
        Long userId = (Long) request.getAttribute("userId");
        studyMaterialService.deleteMaterial(userId, id);
        return Result.success("删除成功", null);
    }
    
    @PostMapping("/file/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = IdUtil.simpleUUID() + suffix;
        minioUtil.uploadFile(file, fileName);
        return Result.success("文件上传成功", fileName);
    }
    
    @GetMapping("/file/{fileName}")
    public Result<String> getFileUrl(@PathVariable String fileName) throws Exception {
        String url = minioUtil.getPresignedUrl(fileName, 7);
        return Result.success(url);
    }
}
