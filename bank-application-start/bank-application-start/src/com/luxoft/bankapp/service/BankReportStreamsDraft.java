//exercise 3.1 create the BankReportStreams Class
package com.luxoft.bankapp.service;

//exercise 3.1 add new libraries
import java.util.*;
import java.util.stream.Collectors;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

public class BankReportStreamsDraft {

    private Bank bank;

    public BankReportStreamsDraft(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public int getNumberOfClients() {
        return (int) bank.getClients().stream().count();
    }

    public int getNumberOfAccounts() {
        return (int) bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .distinct()
                .count();
    }

    public SortedSet<Client> getClientsSorted() {
        return bank.getClients().stream()
                .sorted(Comparator.comparing(client -> Objects.requireNonNullElse(client.getName(), "")))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public double getTotalSumInAccounts() {
        return Math.round(bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .mapToDouble(account -> account.getBalance())
                .sum() * 100) / 100d;
    }

    public SortedSet<Account> getAccountsSortedBySum() {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingDouble(Account::getBalance))));
    }

    public double getBankCreditSum() {
        return Math.round(bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .filter(account -> account instanceof CheckingAccount)
                .filter(account -> account.getBalance() < 0)
                .mapToDouble(account -> -account.getBalance())
                .sum() * 100) / 100d;
    }

    public Map<Client, List<Account>> getCustomerAccounts() {
        return bank.getClients().stream()
                .collect(Collectors.toMap(client -> client, client -> new ArrayList<>(client.getAccounts())));
    }

    public Map<String, List<Client>> getClientsByCity() {
        return bank.getClients().stream()
                .collect(Collectors.groupingBy(Client::getCity, TreeMap::new, Collectors.toList()));
    }
}
