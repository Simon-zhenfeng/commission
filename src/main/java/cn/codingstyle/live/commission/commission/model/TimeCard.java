package cn.codingstyle.live.commission.commission.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class TimeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startTime;
    private Date endTime;
    private String hostName;

    public TimeCard() {
    }

    public TimeCard(String timesheet) {
        //"2021-04-11 23:00:00 | 2021-04-12 01:00:00 | LJQ"
        String[] array = timesheet.split("\\|");
        startTime = parseDate(array[0]);
        endTime = parseDate(array[1]);
        this.hostName = array[2].trim();
    }

    private Date parseDate(String dataString) {
        //todo: sdf has miti thread error, solve the print stack
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
