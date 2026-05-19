package com.ai.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ai.learning.dto.StudyMaterialQueryDTO;
import com.ai.learning.dto.StudyMaterialUploadDTO;
import com.ai.learning.entity.StudyMaterial;
import com.ai.learning.exception.BusinessException;
import com.ai.learning.mapper.StudyMaterialMapper;
import com.ai.learning.service.StudyMaterialService;
import com.ai.learning.vo.StudyMaterialVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class StudyMaterialServiceImpl implements StudyMaterialService {
    
    private final StudyMaterialMapper studyMaterialMapper;
    
    @Override
    public Long uploadMaterial(Long userId, StudyMaterialUploadDTO uploadDTO) {
        StudyMaterial studyMaterial = new StudyMaterial();
        BeanUtil.copyProperties(uploadDTO, studyMaterial);
        studyMaterial.setUserId(userId);
        if (studyMaterial.getStatus() == null) {
            studyMaterial.setStatus(1);
        }
        studyMaterialMapper.insert(studyMaterial);
        return studyMaterial.getId();
    }
    
    @Override
    public IPage<StudyMaterialVO> queryMaterialList(Long userId, StudyMaterialQueryDTO queryDTO) {
        Page<StudyMaterial> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<StudyMaterial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyMaterial::getUserId, userId);
        
        if (StringUtils.hasText(queryDTO.getTitle())) {
            wrapper.like(StudyMaterial::getTitle, queryDTO.getTitle());
        }
        if (StringUtils.hasText(queryDTO.getFileType())) {
            wrapper.eq(StudyMaterial::getFileType, queryDTO.getFileType());
        }
        if (StringUtils.hasText(queryDTO.getCategory())) {
            wrapper.eq(StudyMaterial::getCategory, queryDTO.getCategory());
        }
        if (StringUtils.hasText(queryDTO.getTags())) {
            wrapper.like(StudyMaterial::getTags, queryDTO.getTags());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(StudyMaterial::getStatus, queryDTO.getStatus());
        }
        
        wrapper.orderByDesc(StudyMaterial::getCreateTime);
        
        IPage<StudyMaterial> materialPage = studyMaterialMapper.selectPage(page, wrapper);
        
        Page<StudyMaterialVO> voPage = new Page<>(materialPage.getCurrent(), materialPage.getSize(), materialPage.getTotal());
        voPage.setRecords(BeanUtil.copyToList(materialPage.getRecords(), StudyMaterialVO.class));
        
        return voPage;
    }
    
    @Override
    public StudyMaterialVO getMaterialById(Long userId, Long id) {
        StudyMaterial studyMaterial = studyMaterialMapper.selectById(id);
        if (studyMaterial == null) {
            throw new BusinessException("资料不存在");
        }
        if (!studyMaterial.getUserId().equals(userId)) {
            throw new BusinessException("无权访问该资料");
        }
        return BeanUtil.copyProperties(studyMaterial, StudyMaterialVO.class);
    }
    
    @Override
    public void deleteMaterial(Long userId, Long id) {
        StudyMaterial studyMaterial = studyMaterialMapper.selectById(id);
        if (studyMaterial == null) {
            throw new BusinessException("资料不存在");
        }
        if (!studyMaterial.getUserId().equals(userId)) {
            throw new BusinessException("无权删除该资料");
        }
        studyMaterialMapper.deleteById(id);
    }
}
