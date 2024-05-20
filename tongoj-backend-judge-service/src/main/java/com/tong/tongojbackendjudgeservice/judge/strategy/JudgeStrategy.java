package com.tong.tongojbackendjudgeservice.judge.strategy;


import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 执行判题（拿到沙箱判题结果后的处理）
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
