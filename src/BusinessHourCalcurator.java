import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by goodfeel on 2018. 1. 7..
 */
public class BusinessHourCalcurator {
    public static long getSeconds(LocalDateTime begin, LocalDateTime end) {

        if (begin.isAfter(end)) {
            return 0;
        }

        long seconds = 0;
        long diffDays = LocalDate.from(begin).until(LocalDate.from(end), ChronoUnit.DAYS);


        if (diffDays == 0) {
            seconds = BusinessHour.getMinsByDateTime(begin, end);
        } else if (diffDays == 1) {
            seconds = 0;

            seconds += BusinessHour.getMinsByDateTime(begin, null);
            seconds += BusinessHour.getMinsByDateTime(null, end);

        } else {
            seconds += BusinessHour.getMinsByDateTime(begin, null);

            LocalDate startDay = LocalDate.from(begin).plusDays(1);
            LocalDate finishDay = LocalDate.from(end);

            do {
                seconds += BusinessHour.getMinsByDate(startDay);
                startDay = startDay.plusDays(1);
            } while(startDay.isBefore(finishDay));

            seconds += BusinessHour.getMinsByDateTime(null, end);
        }

        return seconds;
    }


}
