package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Gender;
import com.luxoft.bankapp.email.Email;
import com.luxoft.bankapp.email.EmailException;
import com.luxoft.bankapp.email.EmailService;

public class EmailServiceTest {

    @Test
    public void testSendMail() throws InterruptedException {
        Client client = new Client("Alice Smith", Gender.FEMALE);
        client.setCity("New York");
        client.setEmailAddress("alice_smith@yahoo.com");

        Client clientTo = new Client("Bob Smith", Gender.MALE);
        clientTo.setCity("Boston");
        clientTo.setEmailAddress("bob_smith@yahoo.com");

        Client clientCopy = new Client("Dylan Smith", Gender.MALE);
        clientCopy.setCity("New York");
        clientCopy.setEmailAddress("dylan_smith@yahoo.com");

        EmailService emailService = new EmailService();
        for (int i=0; i<5; i++) {
            try {
                emailService.sendNotificationEmail(
                        new Email()
                                .setFrom(client)
                                .setTo(clientTo)
                                .setCopy(clientCopy)
                                .setTitle("Hello there!")
                                .setBody("How are you?")
                );
            } catch (EmailException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

        assertEquals(5, emailService.getSentEmails());

    }

}
