package withpatterns.observer.impl;

import withpatterns.observer.UserObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditLogObserver implements UserObserver {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogObserver.class);
    @Override
    public void update(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("[AUDIT] {} - {}", timestamp, message);
    }
}