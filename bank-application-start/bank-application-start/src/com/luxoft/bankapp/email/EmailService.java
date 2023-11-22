package com.luxoft.bankapp.email;

//exercise 2.2 create EmailService Class
public class EmailService implements Runnable {

    private Queue queue = new Queue();
    private boolean closed;
    private int sentEmails = 0;

    //exercise 2.4 create and start the Thread
    public EmailService() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        Email email;
        while (true) {
            if(closed) {
                return;
            }
            if ((email = queue.get()) != null) {
                sendEmail(email);
            }
            try {
                synchronized(queue) {
                    //exercise 2.5 implement the asynchronous mechanism wait
                    queue.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    public int getSentEmails() {
        return sentEmails;
    }

    private void sendEmail(Email email) {
        System.out.println(email);
        sentEmails++;
    }

    //exercise 2.2 define the sendNotificationEmail(Email) method
    public void sendNotificationEmail(Email email) throws EmailException {
        if (!closed) {
            queue.add(email);
            //exercise 2.5 implement the asynchronous mechanism notify
            synchronized(queue) {
                queue.notify();
            }
        } else
            throw new EmailException("The mailbox is no longer opened!");
    }

    //exercise 2.6 implement the close method
    public void close() {
        closed = true;
        //exercise 2.5 implement the asynchronous mechanism notify
        synchronized(queue) {
            queue.notify();
        }
    }

}
