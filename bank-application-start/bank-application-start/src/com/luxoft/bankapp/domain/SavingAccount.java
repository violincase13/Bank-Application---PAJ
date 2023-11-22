package com.luxoft.bankapp.domain;


public class SavingAccount extends AbstractAccount {
	public SavingAccount(int id, double balance) {
		super(id, balance);
	}
	
	public double maximumAmountToWithdraw(){
        return getBalance();
    }

	//exercise 3.2 update the toString method
	@Override
	public String toString() {
		return "Saving account " + getId() + ", balance: " + getBalance();
	}
	
}
