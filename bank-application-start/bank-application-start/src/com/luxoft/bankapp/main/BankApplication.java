package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.email.EmailService;

public class BankApplication {
	
	private static Bank bank;

	//exercise 2.6 define the emailService parameter
	private static EmailService emailService;

	public static void main(String[] args) {
		bank = new Bank("ING");
		modifyBank();
		printBalance();
		BankService.printMaximumAmountToWithdraw(bank);

		//exercise 1.5 define the command line
		if (args.length > 0 && args[0].equals("-statistics")) {
			// Display statistics
            displayStatistics(bank);
        }

		//exercise 2.6 call the close method from EmailService
		if (emailService != null) {
			emailService.close();
		}
		// exercise 2.6 ensure that the JVM shuts down properly
		System.exit(0);
	}
	
	private static void modifyBank() {
		Client client1 = new Client("John", Gender.MALE);
		Account account1 = new SavingAccount(1, 100);
		Account account2 = new CheckingAccount(2, 100, 20);
		client1.addAccount(account1);
		client1.addAccount(account2);
		
		try {
		   BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
			System.out.format("Cannot add an already existing client: %s%n", client1.getName());
	    } 

		account1.deposit(100);
		try {
		  account1.withdraw(10);
		} catch (OverdraftLimitExceededException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(90);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(100);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
		  System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 
	}
	
	private static void printBalance() {
		System.out.format("%nPrint balance for all clients%n");
		for(Client client : bank.getClients()) {
			System.out.println("Client: " + client);
			for (Account account : client.getAccounts()) {
				System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
			}
		}
	}

	//exercise 1.5 function to display the bank statistics
	private static void displayStatistics(Bank bank) {
		BankReport bankReport = new BankReport(bank);
		System.out.println("Total No of Accounts: " + bankReport.getNumberOfAccounts());
		System.out.println("Display the clients alphabetically sorted: " + bankReport.getClientsSorted());
		System.out.println("Total Balance from the account: " + bankReport.getTotalSumInAccounts());
		System.out.println("Display Accounts Sorted By Sum: " + bankReport.getAccountsSortedBySum());
		System.out.println("Total amount of the credits: " + bankReport.getBankCreditSum());
		System.out.println("The map of client accounts: " + bankReport.getCustomerAccounts());
		System.out.println("Display clients by city: " + bankReport.getClientsByCity());
	}

}
