package withpatterns.observer.impl;

import withpatterns.observer.UserObserver;

public class EmailNotificationObserver implements UserObserver {
    @Override
    public void update(String message) {
        System.out.println("[EMAIL] Simulando envio de e-mail: " + message);
    }
}