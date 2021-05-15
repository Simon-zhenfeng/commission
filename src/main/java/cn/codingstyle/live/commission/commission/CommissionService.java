package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.TimeCard;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.time.YearMonth.parse;
import static java.util.Arrays.asList;

@Service
public class CommissionService {
    private final TimeCardDao timeCardDao;
    private final OrderDao orderDao;

    public CommissionService(TimeCardDao timeCardDao, OrderDao orderDao) {
        this.timeCardDao = timeCardDao;
        this.orderDao = orderDao;
    }

    public List<Pair<String, BigDecimal>> getCommissions(String yearMonth) {
        Pair<Date, Date> range = getRange(yearMonth);
        List<TimeCard> timesheet = timeCardDao.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(range.getFirst(), range.getSecond());
        TimeCard timeCard = timesheet.get(0);
        BigDecimal amount = orderDao.getSumAmountByCreateTimeBetween(timeCard.getStartTime(), timeCard.getEndTime());
        BigDecimal commission = getCommission(amount);
        return asList(Pair.of(timeCard.getHostName(), commission));
    }

    private BigDecimal getCommission(BigDecimal amount) {
        return amount;
    }

    private Pair<Date, Date> getRange(String yearMonth) {
        YearMonth lastMonth = parse(yearMonth, DateTimeFormatter.ofPattern("yyyyMM")).minusMonths(1);
        Date startDate = Date.from(
                lastMonth.atDay(1).atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant()
        );
        Date endDate = Date.from(
                lastMonth.atEndOfMonth().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()
        );
        return Pair.of(startDate, endDate);
    }

    private String getLastMonth(String yearMonth) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMM");
        return parse(yearMonth, fmt).minusMonths(1).format(fmt);
    }
}
