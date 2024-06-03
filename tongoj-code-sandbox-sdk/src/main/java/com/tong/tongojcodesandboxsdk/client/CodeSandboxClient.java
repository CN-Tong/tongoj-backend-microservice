package com.tong.tongojcodesandboxsdk.client;

import cn.hutool.http.HttpRequest;
import com.tong.tongojcodesandboxsdk.enums.SignAuthReqHeaderEnum;
import com.tong.tongojcodesandboxsdk.utils.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 */
@Data
public class CodeSandboxClient {

    private String accessKey;
    private String secretKey;

    // private final String INTERFACE_PREFIX = "http://47.94.14.69:8090";
    private final String INTERFACE_PREFIX = "http://localhost:8101";

    public CodeSandboxClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 请求Docker代码沙箱
     * @param executeCodeReqStr
     * @return
     */
    public String executeCodeByDocker(String executeCodeReqStr) {
        String executeCodeRespStr = HttpRequest.post(INTERFACE_PREFIX + "/executeCode/docker")
                // 签发签名
                .addHeaders(getHeaderMap(executeCodeReqStr))
                .body(executeCodeReqStr)
                .execute()
                .body();
        System.out.println("Docker Code Sandbox res = " + executeCodeRespStr);
        return executeCodeRespStr;
    }

    /**
     * 请求Java原生代码沙箱
     * @param executeCodeReqStr
     * @return
     */
    public String executeCodeByJavaNative(String executeCodeReqStr) {
        String executeCodeRespStr = HttpRequest.post(INTERFACE_PREFIX + "/executeCode/native")
                // 签发签名
                .addHeaders(getHeaderMap(executeCodeReqStr))
                .body(executeCodeReqStr)
                .execute()
                .body();
        System.out.println("Java Native Code Sandbox res = " + executeCodeRespStr);
        return executeCodeRespStr;
    }

    /**
     * 生成签发签名的请求头
     * @param body
     * @return
     */
    private Map<String, String> getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put(SignAuthReqHeaderEnum.ACCESS_KEY.getValue(), accessKey);
        hashMap.put(SignAuthReqHeaderEnum.BODY.getValue(), body);
        hashMap.put(SignAuthReqHeaderEnum.TIMESTAMP.getValue(), String.valueOf(System.currentTimeMillis()));
        hashMap.put(SignAuthReqHeaderEnum.SIGN.getValue(), SignUtils.getSign(body, secretKey));
        return hashMap;
    }

}
