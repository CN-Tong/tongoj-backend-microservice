package com.tong.tongojbackendquestionservice.controller.inner;

import com.tong.tongojbackendmodel.model.entity.Question;
import com.tong.tongojbackendmodel.model.entity.QuestionSubmit;
import com.tong.tongojbackendquestionservice.service.QuestionService;
import com.tong.tongojbackendquestionservice.service.QuestionSubmitService;
import com.tong.tongojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    public Question getQuestionById(long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    public QuestionSubmit getQuestionSubmitById(long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    public boolean updateQuestionSubmitById(QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}
