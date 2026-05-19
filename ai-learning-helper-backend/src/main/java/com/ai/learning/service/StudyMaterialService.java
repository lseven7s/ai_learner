package com.ai.learning.service;

import com.ai.learning.dto.StudyMaterialQueryDTO;
import com.ai.learning.dto.StudyMaterialUploadDTO;
import com.ai.learning.vo.StudyMaterialVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface StudyMaterialService {
    
    Long uploadMaterial(Long userId, StudyMaterialUploadDTO uploadDTO);
    
    IPage<StudyMaterialVO> queryMaterialList(Long userId, StudyMaterialQueryDTO queryDTO);
    
    StudyMaterialVO getMaterialById(Long userId, Long id);
    
    void deleteMaterial(Long userId, Long id);
}
