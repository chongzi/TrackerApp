package com.meb.tracker.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SignUtils {
    public static String _appId = "35b6k0li04o0";
    public static String _appSecret = "Gd71paMIK2UW3Z8d8YQnqMhcF8dWoVluqH3cgtggGGI=";

    public static Map<String, String> GetQueryParamsFromQueryString(String url) {
        if (url.indexOf("?") == -1)
            return null;
        String qs = url.split("\\?")[1];
        if (qs == null || qs == "")
            return null;

        String[] segments = qs.split("\\&");

        Map<String, String> queryParams = new HashMap<String, String>();

        if (segments != null && segments.length > 0) {
            for (String item : segments) {
                String[] keyPair = item.split("\\=");
                String value = keyPair.length == 2 ? keyPair[1] : "";
                queryParams.put(keyPair[0], value);
            }

        }

        return queryParams;
    }

    public static Map<String, String> AppendGatewayHeaders(Map<String, String> headers, Map<String, String> queryParams)
            throws UnsupportedEncodingException {
        if (headers == null)
            headers = new HashMap<String, String>();

//		long timestamp =  LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        long timestamp = System.currentTimeMillis();

        // long timestamp = System.currentTimeMillis();
        String nonce = UUID.randomUUID().toString().replaceAll("-", "");
        //System.out.println(1111111);
        Map<String, String> toSignDic = new HashMap<String, String>();
        toSignDic.put("appid", _appId);
        toSignDic.put("timestamp", String.valueOf(timestamp));// String.valueOf(timestamp));
        toSignDic.put("nonce", nonce);
        for (String key : headers.keySet()) {// keySet鑾峰彇map闆嗗悎key鐨勯泦鍚� 聽鐒跺悗鍦ㄩ亶鍘唊ey鍗冲彲
            String value = headers.get(key).toString();//
            toSignDic.put(key, value);
        }
        if (queryParams != null) {

            for (String key : queryParams.keySet()) {// keySet鑾峰彇map闆嗗悎key鐨勯泦鍚� 聽鐒跺悗鍦ㄩ亶鍘唊ey鍗冲彲
                String value = queryParams.get(key).toString();//
                toSignDic.put(key, value);
            }
        }
        //System.out.println(33333);
        String str = AsciiMap(toSignDic) + "secret=" + _appSecret;
        System.out.println(str + "==============================");
        String signStr = new SHA1Utils().encode(str);

        // 杞琤ase64
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] textByte = signStr.getBytes("UTF-8");
        // 缂栫爜
        final String sign = encoder.encodeToString(textByte);
        System.out.println(sign + "----------------------");
        toSignDic.put("sigture", sign);
        return toSignDic;

    }

    public static String getSigture(Map<String, String> queryParams) {
        try {
            String str = AsciiMap(queryParams) + "secret=" + _appSecret;
            System.out.println(str + "==============================");
            String signStr = new SHA1Utils().encode(str);

            // 杞琤ase64
            final Base64.Encoder encoder = Base64.getEncoder();
            final byte[] textByte = signStr.getBytes("UTF-8");
            // 缂栫爜
            final String sign = encoder.encodeToString(textByte);
            System.out.println(sign + "----------------------");

            return sign;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 鎺掑簭
     *
     * @param map
     * @return
     */
    public static String AsciiMap(Map<String, String> map) {

        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 瀵规墍鏈変紶鍏ュ弬鏁版寜鐓у瓧娈靛悕鐨� ASCII 鐮佷粠灏忓埌澶ф帓搴忥紙瀛楀吀搴忥級
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 鏋勯�犵鍚嶉敭鍊煎鐨勬牸寮�
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }

            }
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
}
