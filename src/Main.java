import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {
        LocalDateTime begin = LocalDateTime.of(2018, 2, 28, 23, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, 3, 2, 10, 0, 0);

        Duration dur = Duration.between(begin, end);
        System.out.println(dur.toDays());

        System.out.println("until datetime");
        System.out.println(begin.until(end, ChronoUnit.DAYS));

        LocalDate beginDT = LocalDate.from(begin);
        LocalDate endDT = LocalDate.from(end);

        System.out.println("until date");
        System.out.println(beginDT.until(endDT, ChronoUnit.DAYS));

        System.out.println("dur date");
        dur = Duration.between(beginDT, endDT);
        System.out.println(dur.toDays());
    }




}
