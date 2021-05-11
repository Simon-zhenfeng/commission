package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    BigDecimal getSumByCreateTimeBetween(Date startTime, Date endTime);
}
