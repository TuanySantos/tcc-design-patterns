package withPatterns.observer.impl;

import withPatterns.observer.UserObserver;
public class EmailNotificationObserver implements UserObserver {

//    @Override
    public void update(String message) {
        // Simula o envio de e-mail
        sendEmail(message);
    }

    private void sendEmail(String message) {
        // Simula o envio de um e-mail
        System.out.println("Enviando e-mail: " + message);
    }
}
