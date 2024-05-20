package com.tong.tongojbackendjudgeservice.judge;


import com.tong.tongojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.tong.tongojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.tong.tongojbackendjudgeservice.judge.strategy.JudgeContext;
import com.tong.tongojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;

/**
 * 判题管理（简化调用）
 */
public class JudgeManager {

    /**
     * 根据编程语言调用不同的判题策略
     * @param judgeContext
     * @return
     */
    public static JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
