import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by goodfeel on 2018. 1. 6..
 */
public class BusinessHourTest {

    public static final int SECONDS_OF_ONE_MINUTE = 60;
    public static final int MINUTES_OF_ONE_HOUR = 60;
    public static final int WORKING_HOUR_OF_DAY = 10;

    public static final int SECONDS_OF_ONE_WORKING_DAY = WORKING_HOUR_OF_DAY * MINUTES_OF_ONE_HOUR * SECONDS_OF_ONE_MINUTE;

    // 하루 단위의 테스트

    @Test
    // 토요일은 근무시간이 0분
    public void testWeekends() {

        LocalDate saturday = LocalDate.of(2018, 1, 6);
        assertEquals(0, BusinessHour.calSecondsByDate(saturday));
    }

    @Test
    // 주중은 근무시간이 600분
    public void testWeekenddays() {

        LocalDate saturday = LocalDate.of(2018, 1, 5);
        assertEquals(SECONDS_OF_ONE_WORKING_DAY, BusinessHour.calSecondsByDate(saturday));
    }

    @Test
    // 휴일은 근무시간이 0분
    public void testHoliday() {
        LocalDate newYearsDay = LocalDate.of(2018, 1, 1);
        assertEquals(0, BusinessHour.calSecondsByDate(newYearsDay));
    }

    // 시작, 종료일 단위의 테스트
    @Test
    public void testInWorkingDate() {
        // 근무일에 시작된 문의

        // 근무 시작 전 등록 된 문의
        LocalDateTime begin = LocalDateTime.of(2018, 1, 5, 8, 0, 0);
        assertEquals(SECONDS_OF_ONE_WORKING_DAY, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무 시작과 근무 종료 사이 등록 된 문의
        int regHour = 15;
        begin = LocalDateTime.of(2018, 1, 5, regHour, 0, 0);
        assertEquals((BusinessHour.END_WORK_HOUR - regHour) * MINUTES_OF_ONE_HOUR * SECONDS_OF_ONE_MINUTE, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무 시작 이후에 등록된 문의
        begin = LocalDateTime.of(2018, 1, 5, 20, 0, 0);
        assertEquals(0, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무일에 종료된 문의
        // 근무 시작 전 종료 된 문의
        LocalDateTime end = LocalDateTime.of(2018, 1, 5, 8, 0, 0);
        assertEquals(0, BusinessHour.calSecondsByDateTime(null, end));

        // 근무 시작과 근무 종료 사이 종료 된 문의
        regHour = 15;
        end = LocalDateTime.of(2018, 1, 5, regHour, 0, 0);
        assertEquals((regHour - BusinessHour.BEGIN_WORK_HOUR) * MINUTES_OF_ONE_HOUR * SECONDS_OF_ONE_MINUTE, BusinessHour.calSecondsByDateTime(null, end));

        // 근무 시작 이후에 종료된 문의
        end = LocalDateTime.of(2018, 1, 5, 20, 0, 0);
        assertEquals(SECONDS_OF_ONE_WORKING_DAY, BusinessHour.calSecondsByDateTime(null, end));
    }

    // 시작, 종료시간 단위의 테스트
    @Test
    public void testInWorkingDateTime() {
        // 근무일에 시작된 문의

        // 근무 시작 전 등록 된 문의
        LocalDateTime begin = LocalDateTime.of(2018, 1, 5, 8, 30, 30);
        assertEquals(SECONDS_OF_ONE_WORKING_DAY, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무 시작과 근무 종료 사이 등록 된 문의
        begin = LocalDateTime.of(2018, 1, 5, 18, 30, 45);
        assertEquals(30 * SECONDS_OF_ONE_MINUTE - 45, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무 시작 이후에 등록된 문의
        begin = LocalDateTime.of(2018, 1, 5, 20, 45, 12);
        assertEquals(0, BusinessHour.calSecondsByDateTime(begin, null));

        // 근무일에 종료된 문의
        // 근무 시작 전 종료 된 문의
        LocalDateTime end = LocalDateTime.of(2018, 1, 5, 8, 22, 14);
        assertEquals(0, BusinessHour.calSecondsByDateTime(null, end));

        // 근무 시작과 근무 종료 사이 종료 된 문의

        end = LocalDateTime.of(2018, 1, 5, 9, 20, 35);
        assertEquals(20 * SECONDS_OF_ONE_MINUTE + 35, BusinessHour.calSecondsByDateTime(null, end));

        // 근무 시작 이후에 종료된 문의
        end = LocalDateTime.of(2018, 1, 5, 20, 43, 22);
        assertEquals(SECONDS_OF_ONE_WORKING_DAY, BusinessHour.calSecondsByDateTime(null, end));
    }

}