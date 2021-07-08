package lecture11;

import java.sql.*;

public class CategoriaCRUD {

    public static void main(String[] args) throws SQLException {
        (new CategoriaCRUD()).run();
    }

    public void run() throws SQLException {
        try(Connection connection = createConnection()) {

            int id = -1;
            String insertSQL = "INSERT INTO Categoria(nome) VALUES ('Example Brand')";
            try(Statement statement = connection.createStatement()) {
                statement.executeUpdate(insertSQL, Statement.RETURN_GENERATED_KEYS);
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    keys.next();
                    id = keys.getInt(1);
                }

                String updateSQL = "UPDATE Categoria SET nome='Example Brand (Updated)' WHERE id = 999999";
                statement.executeUpdate(updateSQL);

                String deleteSQL = "DELETE FROM Categoria WHERE nome = 'Example Brand'";
                statement.executeUpdate(deleteSQL);

            }


        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }


}
