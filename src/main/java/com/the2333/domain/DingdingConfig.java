package com.the2333.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * 钉钉推送配置信息
 *
 * @author lil
 * @create 2021-02-28 19:47
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dingding")
public class DingdingConfig {
    /**
     * 秘钥
     */
    private String secret;

    /**
     * URL上的Access token
     */
    private String token;
}
