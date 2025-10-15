package com.bancario.atm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the ATM application.
 */
public final class ATMApplication {
    private ATMApplication() {
    }

    public static void main(String[] args) {
        List<Account> sampleAccounts = List.of(
                new Account("12345-6", "Maria Silva", new BigDecimal("1500.00")),
                new Account("65432-1", "João Souza", new BigDecimal("2500.00")),
                new Account("77777-7", "Empresa XPTO", new BigDecimal("10000.00"))
        );

        ATMService service = new ATMService(sampleAccounts);
        try (Scanner scanner = new Scanner(System.in)) {
            ConsoleATM consoleATM = new ConsoleATM(service, scanner);
            consoleATM.start();
        }
    }
}
