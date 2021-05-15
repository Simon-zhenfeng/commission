package cn.codingstyle.live.commission.commission;

import cn.codingstyle.live.commission.commission.model.TimeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeCardDao extends JpaRepository<TimeCard, Long> {
    List<TimeCard> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(Date startTime, Date endTime);
}
