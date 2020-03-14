package com.embosfer.katas.bank.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StatementTest {

    @Test
    void supportsEmptyStatements() {

        Statement statement = new Statement();

        assertThat(statement.print()).isEqualTo("date || credit || debit || balance\n");
    }

    @Test
    void supportsStatementsWithMultipleTransactions() {

        Statement statement = new Statement();
        statement.add(Transaction.of(LocalDate.of(2012, 1, 10), Amount.of(1000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 13), Amount.of(2000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 14), Amount.of(-500.0)));

        assertThat(statement.print()).isEqualTo("date || credit || debit || balance\n" +
                "14/01/2012 || || 500.00 || 2500.00\n" +
                "13/01/2012 || 2000.00 || || 3000.00\n" +
                "10/01/2012 || 1000.00 || || 1000.00"
        );
    }

    @Test
    public void supportsStatementFilters_JustDeposits() {
        Statement statement = new Statement();
        statement.add(Transaction.of(LocalDate.of(2012, 1, 10), Amount.of(1000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 13), Amount.of(2000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 14), Amount.of(-500.0)));

        assertThat(statement.printDeposits()).isEqualTo("date || credit || debit || balance\n" +
                "13/01/2012 || 2000.00 || || 3000.00\n" +
                "10/01/2012 || 1000.00 || || 1000.00"
        );
    }

    @Test
    public void supportsStatementFilters_JustWithdrawals() {
        Statement statement = new Statement();
        statement.add(Transaction.of(LocalDate.of(2012, 1, 10), Amount.of(1000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 13), Amount.of(2000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 14), Amount.of(-500.0)));

        assertThat(statement.printWithdrawals()).isEqualTo("date || credit || debit || balance\n" +
                "14/01/2012 || || 500.00 || 2500.00"
        );
    }

    @Test
    public void supportsStatementFilters_ByDate() {
        Statement statement = new Statement();
        statement.add(Transaction.of(LocalDate.of(2012, 1, 10), Amount.of(1000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 13), Amount.of(2000.0)));
        statement.add(Transaction.of(LocalDate.of(2012, 1, 14), Amount.of(-500.0)));

        assertThat(statement.print(LocalDate.of(2042, 6, 6))).isEqualTo("date || credit || debit || balance\n");
        assertThat(statement.print(LocalDate.of(2012, 1, 10))).isEqualTo("date || credit || debit || balance\n" +
                "10/01/2012 || 1000.00 || || 1000.00"
        );
    }

}