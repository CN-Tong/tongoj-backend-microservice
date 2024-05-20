package com.tong.tongojbackendjudgeservice.judge;


import com.tong.tongojbackendmodel.model.entity.QuestionSubmit;

public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId 题目提交id
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
