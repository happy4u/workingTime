import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by goodfeel on 2018. 1. 6..
 */
public class BusinessHour {

    public static final int BEGIN_WORK_HOUR = 9;
    public static final int END_WORK_HOUR = 19;

    public static final ArrayList<DayOfWeek> NON_WORKING_DAY_OF_WEEK = new ArrayList(
            Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

    public static final ArrayList<MonthDay> holidays = new ArrayList<MonthDay>(Arrays.asList(MonthDay.of(1, 1)));

    public static long getMinsByDate(LocalDate day) {

        if (holidays.contains(MonthDay.from(day))) {
            return 0;
        }

        if (NON_WORKING_DAY_OF_WEEK.contains(day.getDayOfWeek())) {
            return 0;
        } else {
            LocalDateTime begin = makeLocalDateTime(BEGIN_WORK_HOUR, day);
            LocalDateTime end = makeLocalDateTime(END_WORK_HOUR, day);
            return getMinsByDateTime(begin, end);
        }
    }


    public static long getMinsByDateTime(LocalDateTime begin, LocalDateTime end) {

        LocalDateTime calBegin, calEnd;

        if (begin == null && end == null) {
            return 0;
        }


        if (begin != null) {
            calBegin = makeLocalDateTime(BEGIN_WORK_HOUR, begin);
            calEnd = makeLocalDateTime(END_WORK_HOUR, begin);

            if(end != null && end.isBefore(calEnd)){
                calEnd = end;
            }

            if (begin.isAfter(calBegin)) {
                calBegin = begin;
            }
        } else {
            calBegin = makeLocalDateTime(BEGIN_WORK_HOUR, end);
            calEnd = makeLocalDateTime(END_WORK_HOUR, end);

            if(end.isBefore(calEnd)){
                calEnd = end;
            }
        }

        Duration dur = Duration.between(calBegin, calEnd);
        return Math.max(dur.getSeconds(), 0);
    }


    private static LocalDateTime makeLocalDateTime(int hour, LocalDateTime day) {
        return LocalDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), hour, 0, 0);
    }

    private static LocalDateTime makeLocalDateTime(int hour, LocalDate day) {
        return LocalDateTime.of(day.getYear(), day.getMonthValue(), day.getDayOfMonth(), hour, 0, 0);
    }
}
