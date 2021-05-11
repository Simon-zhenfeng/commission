package cn.codingstyle.live.commission.commission.factory;

import cn.codingstyle.live.commission.commission.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OrderFactory {
    public static Order shipped(String createTime) {
        Order order = new Order();
        order.setStatus(Order.SHIPPED);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            order.setCreateTime(dateFormat.parse(createTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return order;
    }
}
