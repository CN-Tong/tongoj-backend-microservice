package com.tong.tongojbackendjudgeservice.controller.inner;

import com.tong.tongojbackendjudgeservice.judge.JudgeService;
import com.tong.tongojbackendmodel.model.entity.QuestionSubmit;
import com.tong.tongojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
