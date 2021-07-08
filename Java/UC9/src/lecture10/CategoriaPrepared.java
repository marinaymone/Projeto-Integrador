package lecture10;

import java.sql.*;

public class CategoriaPrepared {
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new CategoriaPrepared()).run();
    }

    public void run() throws SQLException {
        try(Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Super Mercado");
            update(id, "Dolce & Gabbana");

            delete(id);

            id = insert("Versace'); CREATE TABLE IF NOT EXISTS injection (field int); -- ");
            printList();
            delete(id);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }

    public int insert(String nome) throws SQLException {
        String sql = "INSERT INTO Categoria(nome) VALUES(?)";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nome);
            statement.executeUpdate();
            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        }
    }

    public void update(int id, String nome) throws SQLException {
        String sql = "UPDATE Categoria SET nome = ? WHERE id = ?";
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, nome);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE id = ?";
        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void printList() throws SQLException {
        String sql = "SELECT id, nome FROM Categoria";
        try(
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
            System.out.println("Printing Categoria"); //Imprime o número da linha

            resultSet.beforeFirst();
            while (resultSet.next()) {
                int rowNumber = resultSet.getRow(); //Obtem o número da linha
                int id = resultSet.getInt("id"); //Obtem o valor da coluna id
                String nome = resultSet.getNString("nome"); //Obtem o valor da coluna nome

                System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
                System.out.println("Printing row #" + rowNumber); //Imprime o número da linha
                System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

                System.out.println("Id    : " + id); //Imprime o id
                System.out.println("Nome : " + nome); //Imprime a nome
            }
        }
    }
}
