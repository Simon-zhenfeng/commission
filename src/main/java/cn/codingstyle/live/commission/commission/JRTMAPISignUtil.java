package cn.codingstyle.live.commission.commission;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Simon
 * @date 2021/5/15 21:57
 */
public class JRTMAPISignUtil {

    private static final String SECRET = "cdc0f953-fa9c-4fb0-a4eb-36308291b6ea";

    public static String getSign(Map<String, String> commonParams, Map<String, Object> params) {
        commonParams.remove("access_token");
        commonParams.remove("sign_method");
        commonParams.put("param_json", getParamJson(params));
        String preSign = commonParams.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entrySet -> entrySet.getKey() + entrySet.getValue())
                .collect(Collectors.joining());
        String signString = SECRET + preSign + SECRET;
        System.out.println("signString = " + signString);
        return stringToMD5(signString);
    }

    private static String getParamJson(Map<String, Object> params) {
        Map<String, Object> map = new LinkedHashMap<>();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
        return JSONObject.toJSONString(map);
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        while (md5code.length() < 32) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}
