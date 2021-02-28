package com.the2333;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * 描述:
 *
 * @author lil
 * @create 2021-02-28 18:47
 */
@SpringBootTest
public class Encode {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    String encode() {
        String sign = "";
        try {
            Long timestamp = System.currentTimeMillis();
            String secret = "SEC1259e7cd805d8625fa922136e6e2edf509386401a4f421b3fcc863af0930bbec";
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            System.out.println(sign);
            String url = "https://oapi.dingtalk.com/robot/send?access_token=82ecb8ded85eb662670969d1324788deb5533e0f1e719994481b8b224e5a2fe8&timestamp=%d&sign=%s";
            String s = restTemplate.postForObject(String.format(url, timestamp, sign), new HashMap<>(), String.class);
            System.out.println(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public static void requestMsg() throws Exception {
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC1259e7cd805d8625fa922136e6e2edf509386401a4f421b3fcc863af0930bbec";
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        String url = "https://oapi.dingtalk.com/robot/send?access_token=82ecb8ded85eb662670969d1324788deb5533e0f1e719994481b8b224e5a2fe8&timestamp=%d&sign=%s";
        DingTalkClient client = new DefaultDingTalkClient(String.format(url, timestamp, sign));
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // request.setMsgtype("text");
        // OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        // text.setContent("测试文本消息");
        // request.setText(text);
        // OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // at.setAtMobiles(Arrays.asList("17600102333"));
        // // isAtAll类型如果不为Boolean，请升级至最新SDK
        // at.setIsAtAll(false);
        // request.setAt(at);

        // request.setMsgtype("link");
        // OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        // link.setMessageUrl("https://www.dingtalk.com/");
        // link.setPicUrl("");
        // link.setTitle("时代的火车向前开");
        // link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
        // request.setLink(link);

        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle("杭州天气");
        markdown.setText("#### 杭州天气 @156xxxx8827\n" +
                "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
                "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n" +
                "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
        request.setMarkdown(markdown);
        OapiRobotSendResponse response = client.execute(request);
        if (response.isSuccess()) {
            System.out.println(response.getErrcode());
            System.out.println(response.getErrmsg());
        }
    }

    public static void main(String[] args) {
        try {
            requestMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
