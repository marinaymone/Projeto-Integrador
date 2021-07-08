package lecture10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ShoppingPrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new ShoppingPrepared()).run();
    }

    public void run() throws SQLException {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Bourbon POA");
            update(id, "Bourbon POA");
            delete(id);

        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }

    public int insert(String nome) throws SQLException {
        return -1;
    }

    public void update(int id, String nome) throws SQLException {

    }

    public void delete(int id) throws SQLException {

    }
}
