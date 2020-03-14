package com.embosfer.katas.bank.domain;

public class Amount {

    public final double value;

    private Amount(double value) {
        this.value = value;
    }

    public Amount plus(Amount other) {
        return Amount.of(this.value + other.value);
    }

    public Amount minus(Amount other) {
        return Amount.of(this.value - other.value);
    }

    public static Amount of(double amount) {
        return new Amount(amount);
    }

    public boolean isPositive() {
        return this.value > 0.0;
    }

    public boolean isNegative() {
        return this.value < 0.0;
    }
}
