package com.tong.tongojbackendjudgeservice.judge.strategy;


import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;
import com.tong.tongojbackendmodel.model.dto.question.JudgeConfig;
import com.tong.tongojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> outputList = judgeContext.getOutputList();
        List<String> executeOutputList = judgeContext.getExecuteOutputList();
        JudgeConfig judgeConfig = judgeContext.getJudgeConfig();
        // 生成新的JudgeInfo
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(judgeInfo.getMemory());
        judgeInfoResponse.setTime(judgeInfo.getTime());
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WAITING.getValue());
        // 1. 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        if (outputList.size() != executeOutputList.size()) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfoResponse;
        }
        // 2. 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < outputList.size(); i++) {
            if (!outputList.get(i).equals(executeOutputList.get(i))) {
                judgeInfoResponse.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfoResponse;
            }
        }
        // 3. 判题题目的限制是否符合要求
        Long executeMemory = judgeInfo.getMemory();
        Long executeTime = judgeInfo.getTime();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();
        if (executeMemory > memoryLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        if (executeTime > timeLimit) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        // 4. 可能还有其他的异常情况
        // 5. 返回结果
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        return judgeInfoResponse;
    }
}
