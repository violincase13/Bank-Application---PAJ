//exercise 3.2 create the BankReportStreamsTest Class to test the BankReportStreams implemented methods
package com.luxoft.bankapp.tests;

import java.util.*;
import java.util.stream.Collectors;
import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankReportStreams; // Import the BankReportStreams class
import com.luxoft.bankapp.service.BankService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankReportStreamsTest {

    private static Bank bank = new Bank("Test Bank Streams");
    private static BankReportStreams bankReportStreams; // Use the class
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

        bankReportStreams = new BankReportStreams(bank); // Initialize the new class
    }

    // Existing tests...

    @Test
    public void testNumberOfClientsStreams() {
        assertEquals(2, bankReportStreams.getNumberOfClients());
    }

    @Test
    public void testNumberOfAccountsStreams() {
        assertEquals(4, bankReportStreams.getNumberOfAccounts());
    }

    @Test
    public void testGetClientsSorted() {
        SortedSet<Client> clientsSorted = bankReportStreams.getClientsSorted();

        assertNotNull(clientsSorted);
        assertEquals(2, clientsSorted.size());

        List<Client> sortedList = new ArrayList<>(clientsSorted);
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i).getName().compareTo(sortedList.get(i + 1).getName()) <= 0);
        }
    }

    @Test
    public void testTotalSumInAccountsStreams() {
        assertEquals(5500.0, bankReportStreams.getTotalSumInAccounts(), 0);
    }

    @Test
    public void testAccountsSortedBySumStreams() {
        SortedSet<Account> accounts = bankReportStreams.getAccountsSortedBySum();

        assertEquals(1000.0, accounts.first().getBalance(), 0);
        assertEquals(2000.0, accounts.last().getBalance(), 0);
    }

    @Test
    public void testBankCreditSumStreams() {
        assertEquals(0.0, bankReportStreams.getBankCreditSum(), 0);
    }

    @Test
    public void testCustomerAccountsStreams() {
        Map<Client, List<Account>> customerAccounts = bankReportStreams.getCustomerAccounts();
        assertEquals(2, customerAccounts.size());

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
    public void testClientsByCityStreams() {
        Map<String, List<Client>> clientsByCity = bankReportStreams.getClientsByCity();

        assertEquals(1, clientsByCity.get("New York").size());
        assertEquals(1, clientsByCity.get("Boston").size());
    }
}
