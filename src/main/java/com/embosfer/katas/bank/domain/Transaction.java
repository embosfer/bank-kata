package com.embosfer.katas.bank.domain;

import java.time.LocalDate;

public class Transaction {

    public final LocalDate date;
    public final Amount amount;

    private Transaction(LocalDate date, Amount amount) {
        this.date = date;
        this.amount = amount;
    }

    public static Transaction of(LocalDate date, Amount amount) {
        return new Transaction(date, amount);
    }
}
