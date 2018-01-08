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
            // 하루만에 처리가 끝난 경우
            seconds = BusinessHourOneDay.calSecondsByDateTime(begin, end);

        } else if (diffDays == 1) {
            // 이틀만에 처리가 끝난경우
            seconds = 0;

            seconds += BusinessHourOneDay.calSecondsByDateTime(begin, null);
            seconds += BusinessHourOneDay.calSecondsByDateTime(null, end);

        } else {
            // 처리가 이틀 초과인 경우
            seconds += BusinessHourOneDay.calSecondsByDateTime(begin, null);

            LocalDate startDay = LocalDate.from(begin).plusDays(1);
            LocalDate finishDay = LocalDate.from(end);

            do {
                seconds += BusinessHourOneDay.calSecondsByDate(startDay);
                startDay = startDay.plusDays(1);
            } while(startDay.isBefore(finishDay));

            seconds += BusinessHourOneDay.calSecondsByDateTime(null, end);
        }

        return seconds;
    }


}
