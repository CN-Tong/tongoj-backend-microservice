package com.tong.tongojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.tong.tongojbackendcommon.common.ErrorCode;
import com.tong.tongojbackendcommon.exception.BusinessException;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.tong.tongojcodesandboxsdk.client.CodeSandboxClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
@Component
public class DockerCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest, CodeSandboxClient codeSandboxClient) {
        System.out.println("Docker实现代码沙箱");
        String executeCodeRequestStr = JSONUtil.toJsonStr(executeCodeRequest);
        // String url = "http://47.94.14.69:8090/executeCode/docker";
        // String responseStr = HttpUtil.createPost(url)
        //         .body(executeCodeRequestStr)
        //         .execute()
        //         .body();
        String responseStr = codeSandboxClient.executeCodeByDocker(executeCodeRequestStr);
        if(StrUtil.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode dockerCodeSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
