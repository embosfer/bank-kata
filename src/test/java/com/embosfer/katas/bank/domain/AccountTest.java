package com.embosfer.katas.bank.domain;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AccountTest {

    Account account = new Account(new Statement());

    @Test
    void canDeposit() {
        account.deposit(Transaction.of(LocalDate.of(2020, 3, 12), Amount.of(1000.0)));

        assertThat(account.balance()).isEqualTo(1000.0);
    }

    @Test
    void canWithdrawal() {
        account.withdrawal(Transaction.of(LocalDate.of(2020, 3, 12), Amount.of(1000.0)));

        assertThat(account.balance()).isEqualTo(-1000.0);
    }

    @Test
    public void canPrintStatement() {
        account.deposit(Transaction.of(LocalDate.of(2012, 1, 10), Amount.of(1000.0)));
        account.deposit(Transaction.of(LocalDate.of(2012, 1, 13), Amount.of(2000.0)));
        account.withdrawal(Transaction.of(LocalDate.of(2012, 1, 14), Amount.of(-500.0)));

        PrintStream out = mock(PrintStream.class);
        account.printStatement(out);

        verify(out).println("date || credit || debit || balance\n" +
                "14/01/2012 || || 500.00 || 2500.00\n" +
                "13/01/2012 || 2000.00 || || 3000.00\n" +
                "10/01/2012 || 1000.00 || || 1000.00"
        );
    }

    @Test
    void integrationTest() {
        account.deposit(Transaction.of(LocalDate.of(2020, 3, 12), Amount.of(1000.0)));
        account.withdrawal(Transaction.of(LocalDate.of(2020, 3, 12), Amount.of(1000.0)));

        assertThat(account.balance()).isEqualTo(0.0);
    }

}
