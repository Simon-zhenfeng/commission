package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.Order;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Simon
 * @date 2021/5/15 21:42
 */
public class OrderClient {
    private static final String ORDER_API_URL = "https://openapi-fxg.jinritemai.com/order/searchList?" +
            "app_key={app_key}&method={method}&access_token={access_token}&timestamp={timestamp}&v={v}" +
            "&param_json={param_json}&sign={sign}";

    public List<Order> queryOrder(LocalDateTime startTime, LocalDateTime endTime) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("size", 20);
        params.put("page", 0);
        params.put("create_time_start", startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
        params.put("create_time_end", endTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
        Map<String, String> requestBody = getParam(params, "order.searchList");

        RestTemplate restTemplate = new RestTemplate();
        String responseBody = restTemplate.getForObject(ORDER_API_URL, String.class, requestBody);
        System.out.println("responseBody = " + responseBody);
        JSONObject responseData = parseResponse(responseBody);
        List<Order> orders = new ArrayList<>();
        orders = getOrders(responseData);
        return orders;
    }

    private List<Order> getOrders(JSONObject responseData) {
        final List<Order> orders = new ArrayList<>();
        responseData.getJSONArray("shop_order_list")
                .forEach(item ->{
                    JSONObject orderJson = (JSONObject) item;
                    orders.add(Order.whole(
                            orderJson.getString("order_id"),
                            orderJson.getIntValue("order_status"),
                            orderJson.getIntValue("pay_amount"),
                            orderJson.getIntValue("create_time")
                    ));
                });
        return orders;
    }

    private JSONObject parseResponse(String responseBody) {
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        int err_no = jsonObject.getIntValue("err_no");
        if (err_no != 0) {
            throw new RuntimeException(format(
                    "Call Order API Error: Error No: %d, Error Msg: %s",
                    err_no,
                    jsonObject.getString("message")
            ));
        }
        return jsonObject.getJSONObject("data");
    }

    private Map<String, String> getParam(HashMap<String, Object> param, String method) {
        Map<String, String> result = getCommonParam(method);
        result.put("param_json", JSONObject.toJSONString(param));
        result.put("sign", JRTMAPISignUtil.getSign(getCommonParam(method), param));
        return result;
    }

    private Map<String, String> getCommonParam(String method) {
        Map<String, String> result = new HashMap<>();
        result.put("app_key", "6959524271349777951");
        result.put("access_token", "b9e8e8e3-6654-450d-8c1e-9234da785141");
        result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        result.put("v", "2");
        result.put("method", method);
        return result;
    }

}
