package com.tong.tongojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.tong.tongojbackendcommon.common.ErrorCode;
import com.tong.tongojbackendcommon.exception.BusinessException;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.tong.tongojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的代码沙箱）
 */
public class JavaNativeCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("Java原生实现代码沙箱");
        String url = "http://localhost:8090/executeCode/native";
        String executeCodeRequestStr = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(executeCodeRequestStr)
                .execute()
                .body();
        if(StrUtil.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode javaNativeCodeSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
