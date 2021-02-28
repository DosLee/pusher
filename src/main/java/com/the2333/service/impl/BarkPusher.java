package com.the2333.service.impl;

import com.the2333.domain.BarkConfig;
import com.the2333.service.Pusher;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 描述:
 * IOS Bark 推送
 *
 * @author lil
 * @create 2021-02-28 22:45
 */
@Service("barkPush")
public class BarkPusher implements Pusher {

    private final RestTemplate restTemplate;
    private final BarkConfig barkConfig;

    public BarkPusher(RestTemplate restTemplate, BarkConfig barkConfig) {
        this.restTemplate = restTemplate;
        this.barkConfig = barkConfig;
    }

    /**
     * 是否开启子接口推送
     *
     * @return true:推送, false:关闭
     */
    @Override
    public boolean enablePush() {
        return true;
    }

    /**
     * 推送接口
     * @param content 推送内容
     */
    @Override
    public String pusher(String content) {
        return sendMessageAndCopy(content);
    }

    /**
     * 直接推送指定内容
     * https://api.day.app/yourkey/验证码是9527
     *
     * @param content 要推送的内容
     * @return 响应
     */
    public String sendMessage(@NonNull String content) {
        String url = isBlankUrl();
        return restTemplate.postForObject(url + "/" + content, null, String.class);
    }

    /**
     * 自动复制内容到粘贴板
     *
     * https://api.day.app/yourkey/验证码是9527?automaticallyCopy=1&copy=9527
     *
     * @param content 要推送的内容
     * @return 响应
     */
    public String sendMessageAndCopy(@NonNull String content) {
        String url = isBlankUrl();
        return restTemplate.postForObject(url + "/" + content + "?automaticallyCopy=1&copy=" + content, null, String.class);
    }

    /**
     * 获取URL
     */
    public String isBlankUrl() {
        String url = barkConfig.getUrl();
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        return null;
    }
}
