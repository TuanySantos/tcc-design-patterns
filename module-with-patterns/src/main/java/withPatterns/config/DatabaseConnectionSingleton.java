package withPatterns.config;

public class DatabaseConnectionSingleton {

    private static DatabaseConnectionSingleton instance;

    private DatabaseConnectionSingleton() {
        // Inicializa a conexão com o banco de dados
    }

    public static synchronized DatabaseConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionSingleton();
        }
        return instance;
    }
}
