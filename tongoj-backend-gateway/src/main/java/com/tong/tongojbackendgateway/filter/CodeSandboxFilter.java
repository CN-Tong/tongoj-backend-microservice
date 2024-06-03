package com.tong.tongojbackendgateway.filter;

import com.tong.tongojbackendmodel.model.entity.User;
import com.tong.tongojbackendmodel.model.enums.SignAuthReqHeaderEnum;
import com.tong.tongojbackendserviceclient.service.UserFeignClient;
import com.tong.tongojcodesandboxsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调用接口的拦截器
 */
@Slf4j
@Component
public class CodeSandboxFilter implements GlobalFilter, Ordered {

    @Resource
    private UserFeignClient userFeignClient;

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 仅拦截访问代码沙箱的接口
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        RequestPath path = request.getPath();
        if (!path.toString().contains("/executeCode/")) {
            return chain.filter(exchange);
        }
        // 2. 请求日志
        log.info("请求路径：" + path);
        log.info("请求方法：" + request.getMethod());
        log.info("请求参数：" + request.getQueryParams());
        InetAddress sourceAddress = request.getLocalAddress().getAddress();
        log.info("请求来源地址：" + sourceAddress);
        // 3. 用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst(SignAuthReqHeaderEnum.ACCESS_KEY.getValue());
        String timestamp = headers.getFirst(SignAuthReqHeaderEnum.TIMESTAMP.getValue());
        String sign = headers.getFirst(SignAuthReqHeaderEnum.SIGN.getValue());
        String body = headers.getFirst(SignAuthReqHeaderEnum.BODY.getValue());
        // 3.1 根据ak查询user
        User user = userFeignClient.getByAk(accessKey);
        if (user == null) {
            log.info("通行证不合法");
            return handleNoAuth(response);
        }
        // 3.2 时间戳与当前时间不能超过5min
        long currentTime = System.currentTimeMillis() / 1000;
        final long MAX_TIME_DIFF = 5 * 60L;
        assert timestamp != null;
        if (currentTime - Long.parseLong(timestamp) > MAX_TIME_DIFF) {
            log.info("请求已失效");
            return handleNoAuth(response);
        }
        // 3.3 校验签名
        String sk = user.getSecretKey();
        String computeSign = SignUtils.getSign(body, sk);
        if (!computeSign.equals(sign)) {
            log.info("签名不合法");
            return handleNoAuth(response);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
