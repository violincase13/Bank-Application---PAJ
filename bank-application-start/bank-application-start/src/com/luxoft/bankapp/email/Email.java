package com.luxoft.bankapp.email;

//exercise 2.1 add new libraries
import java.util.ArrayList;
import com.luxoft.bankapp.domain.Client;

//exercise 2.1 create Email Class
public class Email {

    //exercise 2.1 define the Client fields
    private Client from;
    private ArrayList<Client> to, copy;
    private String title, body;

    //exercise 2.1 implement the getting and setting methods
    public Client getFrom() {
        return from;
    }

    public Email setFrom(Client from) {
        this.from = from;
        return this;
    }

    public ArrayList<Client> getTo() {
        return to;
    }

    public Email setTo(ArrayList<Client> to) {
        this.to = to;
        return this;
    }

    public Email setTo(Client to) {
        ArrayList<Client> toList = new ArrayList<Client>();
        toList.add(to);
        setTo(toList);
        return this;
    }

    public ArrayList<Client> getCopy() {
        return copy;
    }

    public Email setCopy(ArrayList<Client> copy) {
        this.copy = copy;
        return this;
    }

    public Email setCopy(Client copy) {
        ArrayList<Client> copyList = new ArrayList<Client>();
        copyList.add(copy);
        setCopy(copyList);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Email setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Email setBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("SEND EMAIL:\n");
        result.append("From: ").append(getFrom()).append(" <").append(getFrom().getEmailAddress()).append(">\n");

        appendClients(result, "To", getTo());
        appendClients(result, "Copy", getCopy());

        result.append("Title: ").append(getTitle()).append("\n");
        result.append("Body: ").append(getBody()).append("\n");

        return result.toString();
    }

    private void appendClients(StringBuilder result, String label, ArrayList<Client> clients) {
        result.append(label).append(": ");
        if (clients != null) {
            for (Client c : clients) {
                //System.out.println("Client: " + c);  //line for debugging
                //System.out.println("Email Address: " + c.getEmailAddress());  // line for debugging
                result.append(c).append(" ");
                String emailAddress = c.getEmailAddress();
                if (emailAddress != null) {
                    result.append("<").append(emailAddress).append(">");
                } else {
                    result.append("<unknown email>");
                }
                result.append(", ");
            }
        }
        result.append("\n");
    }
}