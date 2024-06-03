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

import javax.annotation.Resource;

/**
 * 远程代码沙箱（实际调用接口的代码沙箱）
 */
public class JavaNativeCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest, CodeSandboxClient codeSandboxClient) {
        System.out.println("Java原生实现代码沙箱");
        String executeCodeRequestStr = JSONUtil.toJsonStr(executeCodeRequest);
        // String url = "http://localhost:8090/executeCode/native";
        // String responseStr = HttpUtil.createPost(url)
        //         .body(executeCodeRequestStr)
        //         .execute()
        //         .body();
        String responseStr = codeSandboxClient.executeCodeByJavaNative(executeCodeRequestStr);
        if(StrUtil.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode javaNativeCodeSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
