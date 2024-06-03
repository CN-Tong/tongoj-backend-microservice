package com.tong.tongojbackendjudgeservice.judge.codesandbox;


import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.tong.tongojcodesandboxsdk.client.CodeSandboxClient;

/**
 * 代码沙箱接口
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest, CodeSandboxClient codeSandboxClient);
}
