package com.tong.tongojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.tong.tongojbackendcommon.common.ErrorCode;
import com.tong.tongojbackendcommon.exception.BusinessException;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.tong.tongojbackendjudgeservice.judge.strategy.JudgeContext;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.tong.tongojbackendmodel.model.codesandbox.JudgeInfo;
import com.tong.tongojbackendmodel.model.dto.question.JudgeCase;
import com.tong.tongojbackendmodel.model.dto.question.JudgeConfig;
import com.tong.tongojbackendmodel.model.entity.Question;
import com.tong.tongojbackendmodel.model.entity.QuestionSubmit;
import com.tong.tongojbackendmodel.model.enums.ExecuteCodeRespStatusEnum;
import com.tong.tongojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.tong.tongojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.tong.tongojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService{

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1. 传入题目的提交 id，获取题目的信息、提交信息（代码，编程语言）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2. 如果题目提交状态不为等待中，就不用重复执行了
        if(!QuestionSubmitStatusEnum.WAITING.getValue().equals(questionSubmit.getStatus())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3. 更改题目提交的状态为“判题中”，防止重复执行，也能让用户即时看到状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateSuccess = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        // boolean updateSuccess = questionSubmitFeignClient.lambdaUpdate()
        //         .eq(QuestionSubmit::getId, questionSubmit.getId())
        //         .set(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.RUNNING.getValue())
        //         .update();
        if(!updateSuccess){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 4. 调用沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 从判题信息中获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .language(language)
                .code(code)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        // 5. 根据沙箱的判题结果，设置题目的判题状态和信息
        JudgeInfo judgeInfo = new JudgeInfo();
        // 5.1 判断是否编译错误
        if(ExecuteCodeRespStatusEnum.COMPILE_ERROR.getValue().equals(executeCodeResponse.getStatus())){
            judgeInfo.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
        }
        // 5.2 判断是否运行错误
        if(ExecuteCodeRespStatusEnum.RUN_ERROR.getValue().equals(executeCodeResponse.getStatus())){
            judgeInfo.setMessage(JudgeInfoMessageEnum.RUN_ERROR.getValue());
        }
        // 5.3 如果没有编译错误/运行错误，则对执行结果进行判断
        if(ExecuteCodeRespStatusEnum.SUCCESS.getValue().equals(executeCodeResponse.getStatus())){
            // 创建判题上下文
            JudgeContext judgeContext = new JudgeContext();
            String judgeConfigStr = question.getJudgeConfig();
            JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
            judgeContext.setJudgeConfig(judgeConfig);
            judgeContext.setExecuteOutputList(executeCodeResponse.getOutputList());
            List<String> outputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
            judgeContext.setOutputList(outputList);
            judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
            judgeContext.setLanguage(language);
            // 执行判题，生成最终的判题信息
            judgeInfo = JudgeManager.doJudge(judgeContext);
        }
        // 6. 修改数据库
        questionSubmit.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean success = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        // boolean success = questionSubmitFeignClient.lambdaUpdate()
        //         .eq(QuestionSubmit::getId, questionSubmit.getId())
        //         .set(QuestionSubmit::getStatus, QuestionSubmitStatusEnum.SUCCESS.getValue())
        //         .set(QuestionSubmit::getJudgeInfo, JSONUtil.toJsonStr(judgeInfo))
        //         .update();
        if(!success){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题结果更新失败");
        }
        // 7. 返回结果
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
