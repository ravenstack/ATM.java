package com.bancario.atm;

/**
 * Categorias das transações disponíveis.
 */
public enum TransactionType {
    OPENING_BALANCE("Saldo inicial"),
    DEPOSIT("Depósito"),
    WITHDRAW("Saque"),
    TRANSFER_SENT("Transferência enviada"),
    TRANSFER_RECEIVED("Transferência recebida");

    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
