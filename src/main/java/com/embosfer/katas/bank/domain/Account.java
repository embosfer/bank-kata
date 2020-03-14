package com.embosfer.katas.bank.domain;

import java.io.PrintStream;

public class Account {

    private Amount amount = Amount.of(0);
    private final Statement statement;

    public Account(Statement statement) {
        this.statement = statement;
    }

    public double balance() {
        return amount.value;
    }

    public void deposit(Transaction transaction) {
        this.amount = this.amount.plus(transaction.amount);
        statement.add(transaction);
    }

    public void withdrawal(Transaction transaction) {
        this.amount = this.amount.minus(transaction.amount);
        statement.add(transaction);
    }

    public void printStatement(PrintStream out) {
        out.println(statement.print());
    }
}
