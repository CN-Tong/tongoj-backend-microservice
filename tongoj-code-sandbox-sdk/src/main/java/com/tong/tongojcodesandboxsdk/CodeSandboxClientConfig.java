package com.tong.tongojcodesandboxsdk;

import com.tong.tongojcodesandboxsdk.client.CodeSandboxClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client.codesandbox")
@Data
public class CodeSandboxClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public CodeSandboxClient codeSandboxClient(){
        System.out.println("创建 codeSandboxClient Bean 成功");
        return new CodeSandboxClient(accessKey, secretKey);
    }
}
