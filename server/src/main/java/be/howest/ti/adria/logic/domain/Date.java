package be.howest.ti.adria.logic.domain;

import java.time.LocalDate;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {

    }

    public Date(LocalDate localDate) {
        this.day = localDate.getDayOfMonth();
        this.month = localDate.getMonthValue();
        this.year = localDate.getYear();
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Date setDay(int day) {
        this.day = day;
        return this;
    }

    public Date setMonth(int month) {
        this.month = month;
        return this;
    }

    public Date setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Date date = (Date) o;

        if (day != date.day) return false;
        if (month != date.month) return false;
        return year == date.year;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%d-%d-%d", year, month, day);
    }
}
