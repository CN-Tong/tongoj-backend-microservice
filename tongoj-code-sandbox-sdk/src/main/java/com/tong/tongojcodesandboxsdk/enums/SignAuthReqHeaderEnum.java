package com.tong.tongojcodesandboxsdk.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * API签名认证请求头枚举
 * @author Tong
 */
public enum SignAuthReqHeaderEnum {

    ACCESS_KEY("通行证账号", "accessKey"),
    TIMESTAMP("时间戳", "timestamp"),
    BODY("内容", "body"),
    SIGN("签名", "sign");

    private final String text;

    private final String value;

    SignAuthReqHeaderEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static SignAuthReqHeaderEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (SignAuthReqHeaderEnum anEnum : SignAuthReqHeaderEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
