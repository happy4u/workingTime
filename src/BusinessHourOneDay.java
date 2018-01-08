import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by goodfeel on 2018. 1. 6..
 *
 * 하루에 대한 근무 시간만을 계산하는 클래스
 */
public class BusinessHourOneDay {

    // 평일 근무시간 지정
    public static final int BEGIN_WORK_HOUR = 9;
    public static final int END_WORK_HOUR = 19;

    // 근무하지 않는 요일을 지정
    public static final ArrayList<DayOfWeek> NON_WORKING_DAY_OF_WEEK = new ArrayList(
            Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));

    // 공휴일을 지정해주면 근무시간에서 제외 가능함.
    public static final ArrayList<MonthDay> holidays = new ArrayList<MonthDay>(Arrays.asList(MonthDay.of(1, 1)));

    public static long calSecondsByDate(LocalDate day) {

        if (holidays.contains(MonthDay.from(day))) {
            return 0;
        }

        if (NON_WORKING_DAY_OF_WEEK.contains(day.getDayOfWeek())) {
            return 0;
        } else {
            LocalDateTime begin = makeLocalDateTime(BEGIN_WORK_HOUR, day);
            LocalDateTime end = makeLocalDateTime(END_WORK_HOUR, day);
            return calSecondsByDateTime(begin, end);
        }
    }

    /**
     * 하루 단위의 업무 시간을 계산
     * @param 업무 시작시간 (시작이 당일이 아닌 경우 null)
     * @param 업무 종료시간 (종료가 당일이 아닌 경우 null)
     * @return
     */
    public static long calSecondsByDateTime(LocalDateTime begin, LocalDateTime end) {

        LocalDateTime calBegin, calEnd;

        if (begin == null && end == null) {
            return 0;
        }


        if (begin !=null){
            if(NON_WORKING_DAY_OF_WEEK.contains(begin.getDayOfWeek())){
                return 0;
            }
        }
        else {
            if(NON_WORKING_DAY_OF_WEEK.contains(end.getDayOfWeek())){
                return 0;
            }
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
