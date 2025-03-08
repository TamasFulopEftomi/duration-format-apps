package com.eftomi.kata.enums;

import lombok.Getter;

@Getter
public enum TimeUnitDetail {
    SEC(1, "second"),
    MIN(60, "minute"),
    HOUR(3_600, "hour"),
    DAY(86_400, "day"),
    YEAR(31_536_000, "year");

    private final int sec;
    private final String name;

    TimeUnitDetail(int sec, String name) {
        this.sec = sec;
        this.name = name;
    }
}