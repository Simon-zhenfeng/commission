package cn.codingstyle.live.commission.commission.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TimeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startTime;
    private Date endTime;
    private String hostName;

    public TimeCard(String timesheet) {
        String[] array = timesheet.split("\\|");
        this.hostName = array[2].trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getHostName() {
        return hostName;
    }
}
