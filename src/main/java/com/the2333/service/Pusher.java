package com.the2333.service;

/**
 * 描述:
 * 推送接口
 *
 * @author lil
 * @create 2021-02-28 19:50
 */
public interface Pusher {

    /**
     * 是否开启子接口推送
     * @return true:推送, false:关闭
     */
    boolean enablePush();

    /**
     * 推送接口
     * @param content 推送内容
     */
    String pusher(String content);
}
