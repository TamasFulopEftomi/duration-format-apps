package com.eftomi.kata.enums;

import lombok.Getter;

@Getter
public enum TimeUnitDetails {
    SEC(1, "second", " and "),
    MIN(60, "minute", ", "),
    HOUR(3_600, "hour", ", "),
    DAY(86_400, "day", ", "),
    YEAR(31_536_000, "year", "");

    private final int sec;
    private final String name;
    private final String separator;

    TimeUnitDetails(int sec, String name, String separator) {
        this.sec = sec;
        this.name = name;
        this.separator = separator;
    }
}