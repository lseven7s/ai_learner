package com.ai.learning.service.impl;

import com.ai.learning.dto.StudyPlanGenerateDTO;
import com.ai.learning.service.AiModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AiModelServiceImpl implements AiModelService {

    @Override
    public String generateStudyPlanContent(StudyPlanGenerateDTO dto) {
        log.info("调用外部大模型生成学习计划，参数：{}", dto);
        // TODO: 在此处实现调用外部大模型的逻辑
        // 示例：使用 HTTP 客户端调用大模型 API
        // String prompt = buildPrompt(dto);
        // String response = httpClient.post(apiUrl, prompt);
        // return parseResponse(response);
        
        return "这里是预留的外部大模型调用接口位置，待实现具体的大模型集成逻辑。";
    }
}
