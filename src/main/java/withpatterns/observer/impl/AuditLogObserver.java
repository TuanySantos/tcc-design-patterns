package withpatterns.observer.impl;

import withpatterns.observer.UserObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogObserver implements UserObserver {
    @Override
    public void update(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[AUDIT] " + timestamp + " - " + message);
    }
}