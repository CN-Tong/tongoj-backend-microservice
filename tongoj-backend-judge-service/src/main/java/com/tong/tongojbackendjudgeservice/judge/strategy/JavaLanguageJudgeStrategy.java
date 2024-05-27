package com.tong.tongojbackendjudgeservice.judge.strategy;


import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;
import com.tong.tongojbackendmodel.model.dto.question.JudgeConfig;
import com.tong.tongojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.tong.tongojbackendserviceclient.service.QuestionFeignClient;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Java判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy{

    /**
     * 模拟java本身执行时间耗时10ms
     */
    private static final Long JAVA_EXECUTE_TIME = 10L;


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
        System.out.println("outputList: " + outputList);
        System.out.println("executeOutputList: " + executeOutputList);
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
        // 防止运行内存为空
        Long executeMemory = Optional.ofNullable(judgeInfo.getMemory()).orElse(0L);
        // 防止运行时间为空
        Long executeTime = Optional.ofNullable(judgeInfo.getTime()).orElse(0L);
        Long memoryLimit = judgeConfig.getMemoryLimit();
        Long timeLimit = judgeConfig.getTimeLimit();
        if (executeMemory > memoryLimit * 1000) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        if (executeTime > timeLimit + JAVA_EXECUTE_TIME) {
            judgeInfoResponse.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfoResponse;
        }
        // 4. 可能还有其他的异常情况
        // 5. 返回结果
        judgeInfoResponse.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        return judgeInfoResponse;
    }
}
