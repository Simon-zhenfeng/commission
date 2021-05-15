package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Simon
 * @date 2021/5/15 21:36
 */
public class OrderClientTest {
    @Test
    void should_call_the_order_api_success() {
        OrderClient orderClient = new OrderClient();
        List<Order> orders = orderClient.queryOrder(LocalDateTime.now().withYear(2021).withMonth(5).withDayOfMonth(5),
                LocalDateTime.now().withYear(2021).withMonth(5).withDayOfMonth(16));
        assertThat(orders.size()).isEqualTo(2);
    }
}
