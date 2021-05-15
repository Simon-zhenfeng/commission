package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    @Query("select sum(so.amount) from Order so where so.createTime between :startTime and :endTime")
    BigDecimal getSumAmountByCreateTimeBetween(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
