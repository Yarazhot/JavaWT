package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {
    private static final List<Connection> connections = new LinkedList<>();
    private static ConnectionPool pool;
    private static final int POOL_SIZE = 4;

    public static ConnectionPool getConnectionPool() throws ConnectionException{
        if (pool == null) {
            try {
                InitialContext initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:/comp/env");
                DataSource dataSource = (DataSource) envContext.lookup("jdbc/WT-DB");

                for (int i = 0; i < POOL_SIZE; i++) {
                    connections.add(dataSource.getConnection());
                }

                pool = new ConnectionPool();
            } catch (SQLException | NamingException e) {
                throw new ConnectionException(e);
            }
        }
        return pool;
    }

    public Connection getConnection() {
        while (connections.size() == 0) {
            Thread.yield();
        }

        Connection connection = connections.stream().findFirst().orElseThrow(RuntimeException::new);
        connections.remove(connection);
        return connection;
    }

    public void returnConnection(Connection connection) {
        if (!connections.contains(connection)) {
            connections.add(connection);
        }
    }
}
