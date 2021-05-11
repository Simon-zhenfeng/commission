package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.TimeCard;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.time.YearMonth.parse;
import static java.util.Arrays.asList;

@Service
public class CommissionService {
    private TimeCardDao timeCardDao;
    private OrderDao orderDao;

    public CommissionService(TimeCardDao timeCardDao) {
        this.timeCardDao = timeCardDao;
    }

    public List<Pair<String, BigDecimal>> getCommissions(String yearMonth) {
        Pair<Date, Date> range = getRange(yearMonth);
        List<TimeCard> timesheet = timeCardDao.findAllByStartTimeBeforeAndEndTimeAfter(range.getFirst(), range.getSecond());
        TimeCard timeCard = timesheet.get(0);
        BigDecimal amount = orderDao.getSumByCreateTimeBetween(timeCard.getStartTime(), timeCard.getEndTime());
        BigDecimal commission = getCommission(amount);
        return asList(Pair.of(timeCard.getHostName(), amount));
    }

    private BigDecimal getCommission(BigDecimal amount) {
        return null;
    }

    private Pair<Date, Date> getRange(String yearMonth) {
        return null;
    }

    private String getLastMonth(String yearMonth) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMM");
        return parse(yearMonth, fmt).minusMonths(1).format(fmt);
    }
}
