package com.koreait.exam.springbatch_10;

import java.time.LocalDate;
import java.time.YearMonth;

public class test {
    public static void main(String[] args) {
        LocalDate today = LocalDate.parse("2023-02-09");

        System.out.println(today);

        String lastDate = theLastDateOfTheMonth(today);

        System.out.println(lastDate);
    }

    public static String theLastDateOfTheMonth(LocalDate localDate) {

        LocalDate lastDate = localDate.withDayOfMonth(localDate.lengthOfMonth());

        String lastDateToString = lastDate.toString();
        String[] lastDateToStringArr = lastDateToString.split("-");

        return lastDateToStringArr[lastDateToStringArr.length - 1];
    }
}
