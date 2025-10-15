# ATM.java

Sistema bancário em linha de comando desenvolvido em Java, com foco em boas práticas de código e arquitetura orientada a objetos. A aplicação simula o fluxo de um caixa eletrônico simples, permitindo autenticação por número de conta, depósitos, transferências e consulta de extrato.

## Funcionalidades

- **Autenticação de conta**: acesso por número de conta com dados pré-cadastrados.
- **Consulta de saldo**: exibe o saldo disponível em tempo real.
- **Depósitos**: adiciona valores ao saldo com validação de entradas.
- **Transferências entre contas**: movimenta valores entre contas cadastradas com validações de saldo e destino.
- **Extrato completo**: registra todas as operações, incluindo saldo inicial, com data e hora.

## Estrutura do Projeto

```
src/
└── main
    └── java
        └── com
            └── bancario
                └── atm
                    ├── ATMApplication.java   # Classe principal (entry point)
                    ├── ATMService.java       # Regras de negócio e orquestração
                    ├── Account.java          # Entidade de conta bancária
                    ├── ConsoleATM.java       # Interface de linha de comando
                    ├── Transaction.java      # Modelo de transação
                    └── TransactionType.java  # Enumeração dos tipos de transação
```

## Como executar

1. Compile o projeto:
   ```bash
   javac -d out $(find src/main/java -name "*.java")
   ```
2. Execute a aplicação:
   ```bash
   java -cp out com.bancario.atm.ATMApplication
   ```

A aplicação irá solicitar o número da conta. Utilize uma das contas de exemplo cadastradas no código (`12345-6`, `65432-1` ou `77777-7`).

## Próximos passos sugeridos

- Persistir as contas e transações em arquivo ou banco de dados.
- Adicionar cobertura de testes automatizados (JUnit).
- Implementar autenticação com senha e perfis de usuário.
- Criar camadas adicionais (por exemplo, serviço REST) para consumo externo.
