package com.bancario.atm;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Immutable description of an account transaction.
 */
public final class Transaction {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final TransactionType type;
    private final BigDecimal amount;
    private final BigDecimal resultingBalance;
    private final LocalDateTime timestamp;
    private final String description;

    public Transaction(TransactionType type, BigDecimal amount, BigDecimal resultingBalance, String description) {
        this.type = Objects.requireNonNull(type, "type");
        this.amount = amount;
        this.resultingBalance = resultingBalance;
        this.timestamp = LocalDateTime.now();
        this.description = description == null ? "" : description;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getResultingBalance() {
        return resultingBalance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String toDisplayString() {
        return String.format("[%s] %-18s Valor: R$ %s | Saldo após: R$ %s | %s",
                TIMESTAMP_FORMATTER.format(timestamp),
                type.getLabel(),
                amount,
                resultingBalance,
                description);
    }
}
