package com.embosfer.katas.bank.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Predicate;

import static java.util.stream.Collectors.joining;

public class Statement {

    private Deque<Row> rows = new LinkedList<>();
    private Amount balance = Amount.of(0);

    void add(Transaction transaction) {
        this.balance = this.balance.plus(transaction.amount);
        rows.addFirst(toRow(transaction));
    }

    private Row toRow(Transaction transaction) {
        return new Row(transaction.date, transaction.amount, balance);
    }

    public String print() {
        return printWithFilter(row -> true);
    }

    public String print(LocalDate date) {
        return printWithFilter(row -> row.date.equals(date));
    }

    public String printDeposits() {
        return printWithFilter(row -> row.transactionAmount.isPositive());
    }

    public String printWithdrawals() {
        return printWithFilter(row -> row.transactionAmount.isNegative());
    }

    private String printWithFilter(Predicate<Row> filter) {
        return rows.stream()
                .filter(filter)
                .map(Row::toString)
                .collect(joining("\n", Row.HEADER, ""));
    }

    private static class Row {
        private static final String HEADER = "date || credit || debit || balance\n";
        private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        private final LocalDate date;
        private final Amount transactionAmount;
        private final Amount balance;

        public Row(LocalDate date, Amount amount, Amount balance) {
            this.date = date;
            this.transactionAmount = amount;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return String.format("%s || %s || %.2f", DATE_TIME_FORMATTER.format(date), formattedAmount(), balance.value);
        }

        private String formattedAmount() {
            return transactionAmount.isNegative()
                    ? String.format("|| %.2f", -transactionAmount.value)
                    : String.format("%.2f ||", transactionAmount.value);
        }
    }
}
