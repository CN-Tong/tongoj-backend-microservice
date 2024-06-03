package com.tong.tongojbackendjudgeservice.judge.codesandbox;


import com.tong.tongojbackendjudgeservice.judge.codesandbox.impl.DockerCodeSandbox;
import com.tong.tongojbackendjudgeservice.judge.codesandbox.impl.JavaNativeCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱示例）
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱实例
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "java native":
                return new JavaNativeCodeSandbox();
            case "docker":
                return new DockerCodeSandbox();
            default:
                return new DockerCodeSandbox();
        }
    }
}
