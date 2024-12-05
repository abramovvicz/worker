package com.abramovvicz;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.abramovvicz.TextConst.ascii;
import static com.abramovvicz.TextConst.blue;
import static com.abramovvicz.TextConst.endOfColor;
import static com.abramovvicz.TextConst.purple;
import static com.abramovvicz.TextConst.yellow;

class Worker {

    final int durationWork = 4;
    final ZonedDateTime[] currentTime = {ZonedDateTime.now()};
    int dayOfMonth = currentTime[0].getDayOfMonth();
    Month month = currentTime[0].getMonth();
    int hour = currentTime[0].getHour();
    int min = currentTime[0].getMinute();
    ZonedDateTime currentTimePlusDurationWork = currentTime[0].plusHours(durationWork);
    int dayOfMonthPlusDW = currentTimePlusDurationWork.getDayOfMonth();
    Month monthPlusDW = currentTimePlusDurationWork.getMonth();
    int hourPlusDW = currentTimePlusDurationWork.getHour();
    int minPlusDW = currentTimePlusDurationWork.getMinute();
    final Duration[] duration = {Duration.ofHours(4)};
    LocalTime countDown = LocalTime.of(0, 0).plus(duration[0]);
    LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), countDown);
    final ZonedDateTime[] zonedDateTime = {ZonedDateTime.of(localDateTime, ZoneId.systemDefault())};
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final static int terminalWidth = 80;

    public void start() {
        Runnable task = () -> {
            /*to use in future maybe?
            var nowHour = currentTime[0].format(DateTimeFormatter.ofPattern("hh"));
            var nowMin = currentTime[0].format(DateTimeFormatter.ofPattern("mm"));
            var nowSec = currentTime[0].format(DateTimeFormatter.ofPattern("ss"));
             */
            var countHour = zonedDateTime[0].format(DateTimeFormatter.ofPattern("hh"));
            var countMin = zonedDateTime[0].format(DateTimeFormatter.ofPattern("mm"));
            var countSec = zonedDateTime[0].format(DateTimeFormatter.ofPattern("ss"));

            var text = String.format("\r" + blue + " " + month + " " + dayOfMonth + " " + hour + ":" + min + endOfColor + " " + " | " + yellow + " GOAL PENDING FROM 4 HOURS DURATION:" + endOfColor + purple + "m%sh %sm %ss " + endOfColor + " | " + blue + dayOfMonthPlusDW + " " + monthPlusDW + " " + hourPlusDW + ":" + minPlusDW + endOfColor, countHour, countMin, countSec);
            printCentered(text, terminalWidth);
            zonedDateTime[0] = zonedDateTime[0].minusSeconds(1);
            currentTime[0] = currentTime[0].minusSeconds(1);
        };
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(scheduler::shutdown, durationWork, TimeUnit.HOURS);
    }


    public static void printCentered(String text, int terminalWidth) {
        int padding = (terminalWidth - text.length()) / 2;
        System.out.print(" ".repeat(Math.max(0, padding)) + text);
    }

}
