package com.bancario.atm;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a bank account with basic operations such as deposit, withdraw and transfer.
 */
public class Account {
    private final String number;
    private final String holderName;
    private BigDecimal balance;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String number, String holderName, BigDecimal initialBalance) {
        this.number = Objects.requireNonNull(number, "number");
        this.holderName = Objects.requireNonNull(holderName, "holderName");
        this.balance = normalize(initialBalance);
        recordTransaction(TransactionType.OPENING_BALANCE, initialBalance, "Saldo inicial");
    }

    public String getNumber() {
        return number;
    }

    public String getHolderName() {
        return holderName;
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public synchronized List<Transaction> getTransactions() {
        return Collections.unmodifiableList(new ArrayList<>(transactions));
    }

    public synchronized void deposit(BigDecimal amount, String description) {
        BigDecimal normalizedAmount = ensurePositive(amount, "Valor do depósito deve ser positivo");
        balance = balance.add(normalizedAmount);
        recordTransaction(TransactionType.DEPOSIT, normalizedAmount, description);
    }

    public synchronized void withdraw(BigDecimal amount, String description) {
        BigDecimal normalizedAmount = ensurePositive(amount, "Valor do saque deve ser positivo");
        if (balance.compareTo(normalizedAmount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
        }
        balance = balance.subtract(normalizedAmount);
        recordTransaction(TransactionType.WITHDRAW, normalizedAmount, description);
    }

    public synchronized void transferTo(Account destination, BigDecimal amount) {
        Objects.requireNonNull(destination, "destination");
        BigDecimal normalizedAmount = ensurePositive(amount, "Valor da transferência deve ser positivo");
        if (balance.compareTo(normalizedAmount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }
        if (destination == this) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        balance = balance.subtract(normalizedAmount);
        destination.receiveTransferFrom(this, normalizedAmount);
        recordTransaction(TransactionType.TRANSFER_SENT, normalizedAmount,
                "Transferência enviada para " + destination.getNumber());
    }

    private synchronized void receiveTransferFrom(Account source, BigDecimal amount) {
        balance = balance.add(amount);
        recordTransaction(TransactionType.TRANSFER_RECEIVED, amount,
                "Transferência recebida de " + source.getNumber());
    }

    private void recordTransaction(TransactionType type, BigDecimal amount, String description) {
        transactions.add(new Transaction(type, amount, balance, description));
    }

    private static BigDecimal ensurePositive(BigDecimal amount, String message) {
        Objects.requireNonNull(amount, "amount");
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(message);
        }
        return normalize(amount);
    }

    private static BigDecimal normalize(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }
}
