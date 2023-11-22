//package com.luxoft.bankapp.domain;
//
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import com.luxoft.bankapp.exceptions.ClientExistsException;
//import com.luxoft.bankapp.utils.ClientRegistrationListener;
//
//public class Bank {
//
//	private final List<Client> clients = new ArrayList<Client>();
//	private final List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
//
//	private int printedClients = 0;
//	private int emailedClients = 0;
//	private int debuggedClients = 0;
//
//	public Bank() {
//		listeners.add(new PrintClientListener());
//		listeners.add(new EmailNotificationListener());
//		listeners.add(new DebugListener());
//	}
//
//	public int getPrintedClients() {
//		return printedClients;
//	}
//
//	public int getEmailedClients() {
//		return emailedClients;
//	}
//
//	public int getDebuggedClients() {
//		return debuggedClients;
//	}
//
//	public void addClient(final Client client) throws ClientExistsException {
//    	if (clients.contains(client)) {
//    		throw new ClientExistsException("Client already exists into the bank");
//    	}
//
//    	clients.add(client);
//        notify(client);
//	}
//
//	private void notify(Client client) {
//        for (ClientRegistrationListener listener: listeners) {
//            listener.onClientAdded(client);
//        }
//    }
//
//	public List<Client> getClients() {
//		return Collections.unmodifiableList(clients);
//	}
//
//	class PrintClientListener implements ClientRegistrationListener {
//		@Override
//		public void onClientAdded(Client client) {
//	        System.out.println("Client added: " + client.getName());
//	        printedClients++;
//	    }
//
//	}
//
//	class EmailNotificationListener implements ClientRegistrationListener {
//		@Override
//		public void onClientAdded(Client client) {
//	        System.out.println("Notification email for client " + client.getName() + " to be sent");
//	        emailedClients++;
//	    }
//	}
//
//	class DebugListener implements ClientRegistrationListener {
//        @Override
//        public void onClientAdded(Client client) {
//            System.out.println("Client " + client.getName() + " added on: " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
//            debuggedClients++;
//        }
//    }
//
//}


package com.luxoft.bankapp.domain;

import java.text.DateFormat;
import java.util.Collections;
//exercise 1.1 add new libraries
import java.util.HashSet;
import java.util.Set;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.utils.ClientRegistrationListener;

public class Bank {

	//exercise 1.1 add store the client list using Set method
	//exercise 1.4 stores instances of the Client class
	// Clients are added to this set in the addClient method
	//It is used to retrieve an unmodifiable set of clients in the getClients method
	private final Set<Client> clients = new HashSet<>();
	//exercise 1.4 stores instances of classes implementing the ClientRegistrationListener interface
	//The listeners are notified when a client is added in the notify method
	private final Set<ClientRegistrationListener> listeners = new HashSet<>();

	private int printedClients = 0;
	private int emailedClients = 0;
	private int debuggedClients = 0;
	//exercise 3.2 add name as paramater to use it forwardly for tests
	private String name;

	public Bank(String name) {
		//exercise 3.2 create the constructor
		this.name = name;

		listeners.add(new PrintClientListener());
		listeners.add(new EmailNotificationListener());
		listeners.add(new DebugListener());
	}

	public int getPrintedClients() {
		return printedClients;
	}

	public int getEmailedClients() {
		return emailedClients;
	}

	public int getDebuggedClients() {
		return debuggedClients;
	}

	//exercise 1.1 implement access method addClient
	public void addClient(final Client client) throws ClientExistsException {
		if (clients.contains(client)) {
			throw new ClientExistsException("Client already exists in the bank");
		}

		clients.add(client);
		notify(client);
	}

	private void notify(Client client) {
		for (ClientRegistrationListener listener : listeners) {
			listener.onClientAdded(client);
		}
	}

	//exercise 1.1 implement access method getClients
	public Set<Client> getClients() {
		//exercise 1.4 provide an unmodifiable view of the clients set
		//It returns an unmodifiable set of clients using Collections.unmodifiableSet(clients).
		return Collections.unmodifiableSet(clients);
	}

	//exercise 1.1 the equals() method is overridden in the Client class
	public Client getClient(String name) {
		for (Client client: clients)
			if (client.getName().equals(name))
				return client;
		return null;
	}

	class PrintClientListener implements ClientRegistrationListener {
		@Override
		public void onClientAdded(Client client) {
			System.out.println("Client added: " + client.getName());
			printedClients++;
		}
	}

	class EmailNotificationListener implements ClientRegistrationListener {
		@Override
		public void onClientAdded(Client client) {
			System.out.println("Notification email for client " + client.getName() + " to be sent");
			emailedClients++;
		}
	}

	class DebugListener implements ClientRegistrationListener {
		@Override
		public void onClientAdded(Client client) {
			System.out.println("Client " + client.getName() + " added on: " +
					DateFormat.getDateInstance(DateFormat.FULL).format(System.currentTimeMillis()));
			debuggedClients++;
		}
	}
}





