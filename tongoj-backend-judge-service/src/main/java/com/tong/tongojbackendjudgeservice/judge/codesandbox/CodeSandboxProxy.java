package com.tong.tongojbackendjudgeservice.judge.codesandbox;

import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.tong.tongojcodesandboxsdk.client.CodeSandboxClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest, CodeSandboxClient codeSandboxClient) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest, codeSandboxClient);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
