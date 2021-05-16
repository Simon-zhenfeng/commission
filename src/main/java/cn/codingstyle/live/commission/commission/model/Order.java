package cn.codingstyle.live.commission.commission.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "store_order")
public class Order {
    public static final String SHIPPED = "shipped";
    //微支付取消
    public static final String NO_PAYED = "no payed and cancelled";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private Date createTime;
    private String status;
    private BigDecimal amount;

    public static Order whole(String orderId, int orderStatus, int payAmount, int createTime) {
        Order order = new Order();
        order.orderId = orderId;
        order.status = toStatus(orderStatus);
        order.amount = BigDecimal.valueOf(payAmount);
        order.createTime = new Date(createTime * 1000L);
        return order;
    }

    //todo: to be refactor
    private static String toStatus(int orderStatus) {
        if (orderStatus == 4) {
            return NO_PAYED;
        }
        return "";
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
