package com.tong.tongojbackendjudgeservice.judge.strategy;

import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;
import com.tong.tongojbackendmodel.model.dto.question.JudgeConfig;
import lombok.Data;

import java.util.List;

/**
 * 判题上下文（用于定义在策略中要传递的参数）
 */
@Data
public class JudgeContext {

    /**
     * 判题信息（包括time和memory）
     */
    private JudgeInfo judgeInfo;

    /**
     * 判题配置（包括time和memory）
     */
    private JudgeConfig judgeConfig;

    /**
     * 判题输出用例
     */
    private List<String> outputList;

    /**
     * 执行代码实际的输出
     */
    private List<String> executeOutputList;

    /**
     * 代码语言
     */
    private String language;

}
