//exercise 3.1 create the BankReportStreams Class
package com.luxoft.bankapp.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

public class BankReportStreams {

    private Bank bank;

    public BankReportStreams(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public int getNumberOfClients() {
        return (int) bank.getClients().stream().count();
    }

    public int getNumberOfAccounts() {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .collect(Collectors.toSet())
                .size();
    }

    public SortedSet<Client> getClientsSorted() {
        return bank.getClients().stream()
                .sorted(Comparator.comparing(client -> client.getName() == null ? "" : client.getName()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public double getTotalSumInAccounts() {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public SortedSet<Account> getAccountsSortedBySum() {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparingDouble(Account::getBalance))));
    }

    public double getBankCreditSum() {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .filter(account -> account instanceof CheckingAccount && account.getBalance() < 0)
                .mapToDouble(account -> -account.getBalance())
                .sum();
    }

    public Map<Client, List<Account>> getCustomerAccounts() {
        return bank.getClients().stream()
                .collect(Collectors.toMap(
                        client -> client,
                        client -> new ArrayList<>(client.getAccounts())
                ));
    }

    public Map<String, List<Client>> getClientsByCity() {
        return bank.getClients().stream()
                .collect(Collectors.groupingBy(Client::getCity, Collectors.toList()));
    }
}
