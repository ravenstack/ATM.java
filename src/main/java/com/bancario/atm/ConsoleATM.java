package com.bancario.atm;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Simple command line interface for interacting with the ATM service.
 */
public class ConsoleATM {

    private final ATMService service;
    private final Scanner scanner;
    private Account currentAccount;

    public ConsoleATM(ATMService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("===== Sistema Bancário =====");
        authenticateUser();
        boolean running = true;
        while (running) {
            printMenu();
            int option = readInt("Escolha uma opção: ");
            try {
                running = handleOption(option);
            } catch (IllegalArgumentException ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }
        System.out.println("Obrigado por utilizar nosso sistema! Até logo.");
    }

    private void authenticateUser() {
        while (currentAccount == null) {
            System.out.print("Informe o número da sua conta: ");
            String accountNumber = scanner.nextLine().trim();
            service.findAccount(accountNumber).ifPresentOrElse(account -> {
                currentAccount = account;
                System.out.printf("Bem-vindo(a), %s!%n", account.getHolderName());
                System.out.printf("Saldo atual: R$ %s%n", account.getBalance());
            }, () -> System.out.println("Conta não encontrada, tente novamente."));
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Consultar saldo");
        System.out.println("2. Receber valor (depósito)");
        System.out.println("3. Transferir valor");
        System.out.println("4. Extrato");
        System.out.println("5. Sair");
    }

    private boolean handleOption(int option) {
        switch (option) {
            case 1 -> showBalance();
            case 2 -> performDeposit();
            case 3 -> performTransfer();
            case 4 -> showStatement();
            case 5 -> {
                return false;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
        return true;
    }

    private void showBalance() {
        System.out.printf("Saldo disponível: R$ %s%n", currentAccount.getBalance());
    }

    private void performDeposit() {
        BigDecimal amount = readAmount("Informe o valor do depósito: ");
        service.deposit(currentAccount.getNumber(), amount);
        System.out.println("Depósito realizado com sucesso!");
        showBalance();
    }

    private void performTransfer() {
        System.out.print("Informe o número da conta de destino: ");
        String destinationNumber = scanner.nextLine().trim();
        BigDecimal amount = readAmount("Informe o valor da transferência: ");
        service.transfer(currentAccount.getNumber(), destinationNumber, amount);
        System.out.println("Transferência realizada com sucesso!");
        showBalance();
    }

    private void showStatement() {
        System.out.println("===== Extrato de operações =====");
        currentAccount.getTransactions().stream()
                .map(Transaction::toDisplayString)
                .forEach(System.out::println);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private BigDecimal readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().replace(',', '.');
            try {
                BigDecimal amount = new BigDecimal(input.trim());
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new NumberFormatException();
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número positivo.");
            }
        }
    }
}
