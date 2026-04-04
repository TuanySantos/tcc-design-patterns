package withPatterns.observer.impl;

import withPatterns.observer.UserObserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogObserver implements UserObserver{

//    @Override
    public void update(String message) {
        // Gera o log de auditoria com informações sobre o evento.
        logAuditEvent(message);
    }

    private void logAuditEvent(String message) {
        // Obtém a data e hora do evento
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Aqui você pode gravar o log em um arquivo ou banco de dados
        System.out.println("AUDIT LOG - " + timestamp + ": " + message);

        // Exemplo de gravação em um banco de dados ou arquivo
        // LogService.save(new AuditLogEntry(timestamp, message));
    }
}
