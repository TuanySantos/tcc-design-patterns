
package withpatterns.observer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import withpatterns.observer.UserObserver;

public class EmailNotificationObserver implements UserObserver {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationObserver.class);
    @Override
    public void update(String message) {
        logger.info("[EMAIL] Simulando envio de e-mail: {}", message);
    }
}