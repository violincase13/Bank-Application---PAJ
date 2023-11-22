package com.luxoft.bankapp.email;

//exercise 2.3 add new libraries
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//exercise 2.3 create Queue class
public class Queue {

    //exercise 2.3 implement the get and add methods
    private List<Email> emails = Collections.synchronizedList(new LinkedList<Email>());

    public void add(Email email) {
        emails.add(email);
    }

    public Email get() {
        if (emails.size() > 0) {
            return emails.remove(
                    emails.size() - 1);
        }
        return null;
    }

}
