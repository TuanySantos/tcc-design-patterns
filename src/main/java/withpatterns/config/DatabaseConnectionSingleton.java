package withpatterns.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public final class DatabaseConnectionSingleton {

    private static DatabaseConnectionSingleton instance;
    private final DataSource dataSource;

    private DatabaseConnectionSingleton() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:tccdb;DB_CLOSE_DELAY=-1;MODE=LEGACY");
        ds.setUser("sa");
        ds.setPassword("");
        this.dataSource = ds;
    }

    public static synchronized DatabaseConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseConnectionSingleton();
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}