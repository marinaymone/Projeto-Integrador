package lecture10;

import java.sql.*;

public class FavoritosPrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new FavoritosPrepared()).run();
    }

    public void run() throws SQLException {
        try (Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert(1, 2);
            update(id,1, 2);
            delete(id);

        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }

    public int insert(int id_estabelecimento, int id_login) throws SQLException {
        return -1;
    }

    public void update(int id, int id_estabelecimento, int id_login) throws SQLException {

    }

    public void delete(int id) throws SQLException {

    }

}
