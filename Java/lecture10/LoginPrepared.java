package lecture10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginPrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new LoginPrepared()).run();
    }

    public void run() throws SQLException {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Camila Teste", "12345678");
            update(id,"Camila Teste", "12345678");
            delete(id);

        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }

    public int insert(String login, String senha) throws SQLException {
        return -1;
    }

    public void update(int id, String login, String senha) throws SQLException {

    }

    public void delete(int id) throws SQLException {

    }
}
