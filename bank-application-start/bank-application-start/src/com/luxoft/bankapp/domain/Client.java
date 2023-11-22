//package com.luxoft.bankapp.domain;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class Client {
//
//	private String name;
//	private Gender gender;
//	private List<Account> accounts = new ArrayList<Account>();
//
//	public Client(String name, Gender gender) {
//		this.name = name;
//		this.gender = gender;
//	}
//
//	public void addAccount(final Account account) {
//		accounts.add(account);
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public Gender getGender() {
//		return gender;
//	}
//
//	public List<Account> getAccounts() {
//		return Collections.unmodifiableList(accounts);
//	}
//
//	public String getClientGreeting() {
//		if (gender != null) {
//			return gender.getGreeting() + " " + name;
//		} else {
//			return name;
//		}
//	}
//
//	@Override
//	public String toString() {
//		return getClientGreeting();
//	}
//
//}


package com.luxoft.bankapp.domain;

import java.util.Collections;
//exercise 1.1 add new libraries
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client implements Comparable<Client> {

	private String name;
	private Gender gender;
	//exercise 1.3 add city as attribute for the client
	private String city;
	//exercise 1.2 use Set method to store the accounts list
	//exercise 1.4 stores instances of the Account class
	//Accounts are added to this set in various constructors of the Client class using the addAccount method
	//The getAccounts method returns an unmodifiable set of accounts using Collections.unmodifiableSet(accounts)
	//The setAccounts method allows setting a new set of accounts
	private Set<Account> accounts = new HashSet<>();

	//exercise 2.1 add e-mail address as attribute for the client
	private String email_address;

	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public void addAccount(final Account account) {
		accounts.add(account);
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	//exercise 1.3 define getting and setting for the city attribute
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	//exercise 2.1 define getting and setting for the e-mail address attribute
	public String getEmailAddress() {
		return email_address;
	}
	public void setEmailAddress(String email_address) {
		this.email_address = email_address;
	}

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	@Override
	public String toString() {
		return getClientGreeting();
	}

	//exercise 1.1 add equals() method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (gender != other.gender)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	//exercise 1.1 add hashCode() method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	//exercise 3.2 provide a compareTo method that defines the natural ordering of instances of the Client class
	@Override
	public int compareTo(Client other) {
		// Implement comparison logic based on your requirements
		// For example, comparing clients by name
		return Objects.compare(this.getName(), other.getName(), String::compareTo);
	}
}

