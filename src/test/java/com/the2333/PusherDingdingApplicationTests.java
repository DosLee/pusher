package com.the2333;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PusherDingdingApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
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
            JSONObject params = new JSONObject();
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
    }

}
