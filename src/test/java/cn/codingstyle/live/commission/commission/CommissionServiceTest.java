package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.factory.OrderFactory;
import cn.codingstyle.live.commission.commission.model.Order;
import cn.codingstyle.live.commission.commission.model.TimeCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommissionServiceTest {

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private TimeCardDao timeCardDao;

    @Autowired
    private OrderDao orderDao;

    @Test
    void 一个主播_所有订单在上播区间内_所有订单已收货() {
        // Given 一个主播_所有订单在上播区间内_所有订单已收货
        stubTimesheet("2021-04-01 00:00:00 | 2021-04-02 01:00:00 | LJQ");
        Order order1 = OrderFactory.shipped("2021-04-01 23:10:00", BigDecimal.valueOf(500000));
        Order order2 = OrderFactory.shipped("2021-04-01 23:11:00", BigDecimal.valueOf(500000));
        stubOrders(asList(order1, order2));

        // When
        List<Pair<String, BigDecimal>> commissions = commissionService.getCommissions("202105");

        // Then
        assertThat(commissions).hasSize(1);
        assertThat(commissions.get(0).getFirst()).isEqualTo("LJQ");
        assertThat(commissions.get(0).getSecond()).isEqualTo(new BigDecimal("1000000.00"));
    }

    private void stubOrders(List<Order> orders) {
        for (Order order : orders) {
            orderDao.save(order);
        }
    }

    private void stubTimesheet(String timesheet) {
        TimeCard timeCard = new TimeCard(timesheet);
        timeCardDao.save(timeCard);
    }
}
