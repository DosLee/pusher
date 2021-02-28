package com.the2333.controller;

import com.sun.istack.internal.NotNull;
import com.the2333.service.Pusher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 描述:
 * 入口
 *
 * @author lil
 * @create 2021-02-28 23:48
 */
@Slf4j
@RestController
@RequestMapping("api")
public class PushController {

    private final Pusher barkPush;

    public PushController(Pusher pusher) {
        this.barkPush = pusher;
    }

    @PostMapping("pushToPhone")
    public String pushToPhone(@NotNull @RequestParam() String content) {
        if (barkPush.enablePush()) {
            String response = barkPush.pusher(content);
            if (StringUtils.isNotBlank(response)) {
                return response;
            }
        }
        return "failure";
    }
}
