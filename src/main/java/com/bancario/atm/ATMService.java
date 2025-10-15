package com.bancario.atm;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Centraliza regras de negócio relacionadas às contas do ATM.
 */
public class ATMService {
    private final Map<String, Account> accounts = new LinkedHashMap<>();

    public ATMService(Collection<Account> initialAccounts) {
        initialAccounts.forEach(account -> accounts.put(account.getNumber(), account));
    }

    public Optional<Account> findAccount(String number) {
        return Optional.ofNullable(accounts.get(number));
    }

    public Account requireAccount(String number) {
        return findAccount(number)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + number));
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = requireAccount(accountNumber);
        account.deposit(amount, "Depósito realizado no caixa eletrônico");
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = requireAccount(accountNumber);
        account.withdraw(amount, "Saque realizado no caixa eletrônico");
    }

    public void transfer(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount) {
        Account source = requireAccount(sourceAccountNumber);
        Account destination = requireAccount(destinationAccountNumber);
        source.transferTo(destination, amount);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}
