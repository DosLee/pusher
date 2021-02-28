package com.the2333.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 描述:
 * Bark 配置类
 *
 * @author lil
 * @create 2021-02-28 23:17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bark")
public class BarkConfig {

    /**
     * 自定义推送URL
     */
    private String url;
}
