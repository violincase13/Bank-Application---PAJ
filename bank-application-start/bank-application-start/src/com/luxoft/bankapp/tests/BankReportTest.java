package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;
import org.junit.BeforeClass;
import org.junit.Test;

import com.luxoft.bankapp.service.BankReport;

public class BankReportTest {

    private static Bank bank = new Bank("Test Bank");
    private static BankReport bankReport;
    private static Client client1;
    private static Client client2;

    @BeforeClass
    public static void initialize() throws ClientExistsException {
        // Define the clients
        client1 = new Client("Alice", Gender.FEMALE);
        client2 = new Client("Bob", Gender.MALE);

        //Define the clients' cities
        client1.setCity("New York");
        client2.setCity("Boston");

        // Define the clients' accounts
        client1.addAccount(new SavingAccount(1, 1000.0));
        client1.addAccount(new CheckingAccount(2, 1000.0, 100.0));
        client2.addAccount(new SavingAccount(3, 2000.0));
        client2.addAccount(new CheckingAccount(4, 1500.0, 200.0));

        // Set up the bank with clients and accounts for testing
        BankService.addClient(bank, client1);
        BankService.addClient(bank, client2);

        bankReport = new BankReport(bank);
    }

    @Test
    public void testNumberOfClients() {
        assertEquals(2, bankReport.getNumberOfClients()); // Update the expected value based on the actual number of clients
    }

    @Test
    public void testNumberOfAccounts() {
        assertEquals(4, bankReport.getNumberOfAccounts()); // Update the expected value based on the actual number of accounts
    }

    @Test
    public void testClientsSorted() {
        SortedSet<Client> clients = bankReport.getClientsSorted();

        assertEquals(2, clients.size()); // Update the expected value based on the actual number of clients
        assertTrue(clients.first().equals(client1)); // Update the expected client names
        assertTrue(clients.last().equals(client2));
    }

    @Test
    public void testBankCreditSum() {
        assertEquals(0.0, bankReport.getBankCreditSum(), 0); // Update the expected value based on the actual bank credit sum
    }

    // ...
    @Test
    public void testCustomerAccounts() {
        Map<Client, Collection<Account>> customerAccounts = bankReport.getCustomerAccounts();
        assertEquals(2, customerAccounts.size()); // Update the expected value based on the actual number of clients

        // Update the expected account information based on the actual data
        List<String> client1info = new ArrayList<>();
        List<String> client2info = new ArrayList<>();

        for (Account account : customerAccounts.get(client1)) {
            client1info.add(account.toString());
        }

        for (Account account : customerAccounts.get(client2)) {
            client2info.add(account.toString());
        }

        assertEquals(Arrays.asList("Checking account 2, balance: 1000.0", "Saving account 1, balance: 1000.0"), client1info);
        assertEquals(Arrays.asList("Checking account 4, balance: 1500.0", "Saving account 3, balance: 2000.0"), client2info);
    }

    @Test
    public void testClientsByCity() {
        Map<String, ArrayList<Client>> clientsByCity = bankReport.getClientsByCity();

        assertEquals(1, clientsByCity.get("New York").size()); // Update the expected value based on the actual number of clients in the respective city
        assertEquals(1, clientsByCity.get("Boston").size());
    }
}

