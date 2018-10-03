package com.worldpay.task.domain;

public enum Currency {
    USD("US Dollars"), EUR("Euro"), JPY("Japanese Yen");

    private String description;

    Currency(String description) {
        this.description = description;
    }

}
