package chap12.dateTime;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.*;

import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

public class DateTimeExamples {
    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };
    public static void main(String[] args) {
        useOldDate();
        useLocalDate();
        useTemporalAdjuster();
        useDateFormatter();
    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        System.out.println("====== LocalDate, LocalTime ======");
        LocalDate date = LocalDate.of(2017, 9, 21);
        int year = date.getYear(); // 2017
        Month month = date.getMonth(); // SEPTEMBER
        int day = date.getDayOfMonth(); // 21
        DayOfWeek dow = date.getDayOfWeek(); // THURSDAY
        int len = date.lengthOfMonth(); // 31 (3월의 일 수)
        boolean leap = date.isLeapYear(); // false (윤년이 아님)
        System.out.println("LocalDate: " + date);

        LocalDate today = LocalDate.now();
        System.out.println("today: " + today);

        // TemporalField: 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스
        // 열거자 ChronoField :  TemporalField 인터페이스를 정의
        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println("LocalTime: " + time);


        // parse와 문자열로 LocalDate, LocalTime 인스턴스 만들기
        LocalDate date2 = LocalDate.parse("2017-09-21");
        LocalTime time2 = LocalTime.parse("13:45:20");


        System.out.println();
        System.out.println("====== LocalDateTime ======");
        // LocalDateTime을 직접 만드는 방법과 날짜와 시간을 조합하는 방법
        LocalDateTime dt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20); // 2017-09-21T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        // LocalDate의 atTime 메서드에 시간을 제공하거나 LocalTime의 atDate 메서드에 날짜를 제공
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println("LocalDateTime 직접 만들기: " + dt1);
        System.out.println("date, time 조합: " + dt2);
        System.out.println("LocalDate.atTime: " + dt3);

        // LocalDateTime의 toLocalDate나 toLocalTime 메서드로 LocalDate나 LocalTime 인스턴스를 추출
        LocalDate date1 = dt1.toLocalDate();
        System.out.println("toLocalDate() : " + date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println("toLocalTime() : " + time1);

        System.out.println();
        System.out.println("====== Instant : Unix epoch time ======");
        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        System.out.println();
        System.out.println("====== Duration ======");
        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());
        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        System.out.println();
        System.out.println("====== Period ======");
        Period tenDays = Period.between(LocalDate.of(2017, 9, 11),
                LocalDate.of(2017, 9, 21));
        System.out.println(tenDays);

        System.out.println();
        System.out.println("====== JapaneseDate ======");
        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }

    private static void useTemporalAdjuster() {
        System.out.println();
        System.out.println("====== 날짜조정 : withAttribute ======");
        // 절대적인 방식으로 LocalDate의 속성 바꾸기
        LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
        LocalDate date2 = date1.withYear(2011); // 2011-09-21
        LocalDate date3 = date2.withDayOfMonth(25); // 2011-09-25
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-25
        System.out.println(date4);

        System.out.println();
        System.out.println("====== 날짜조정 : plus, minus ======");
        // 상대적인 방식으로 LocalDate 속성 바꾸기
        LocalDate date11 = LocalDate.of(2017, 9, 21); // 2017-09-21
        LocalDate date21 = date1.plusWeeks(1); // 2017-09-28
        LocalDate date31 = date2.minusYears(6); // 2011-09-28
        LocalDate date41 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
        System.out.println(date41);

        System.out.println();
        System.out.println("====== 미리 정의된 TemporalAdjusters ======");
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(lastDayOfMonth());
        System.out.println(date);

        System.out.println();
        System.out.println("====== 커스텀 TemporalAdjusters ======");
        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        System.out.println();
        System.out.println("====== 커스텀 TemporalAdjusters w/ 람다표현식======");
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
    }

    private static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) {
                dayToAdd = 3;
            }
            if (dow == DayOfWeek.SATURDAY) {
                dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }

//        @Override
//        public Temporal adjustInto(Temporal temporal) {
//            return null;
//        }
    }

    private static void useDateFormatter() {
        System.out.println();
        System.out.println("====== format ======");
        LocalDate date = LocalDate.of(2014, 3, 18);
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2014-03-18
        System.out.println("포매터로 문자열: " + s1);
        System.out.println("포매터로 문자열: " + s2);

        LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("문자열  -> 날짜객체: " + date1);

        // 패턴으로 DateTimeFormatter 만들기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("패턴: " + date.format(formatter));
        System.out.println("패턴(Locale.ITALIAN): " + date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);

        System.out.println("DateTimeFormatterBuilder: " + date.format(complexFormatter));
    }
}
