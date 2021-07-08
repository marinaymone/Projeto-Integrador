package lecture11;

import java.sql.*;

public class LoginWithInjection {

    private Connection connection;

    public static void main(String[] args) throws SQLException {
        (new LoginWithInjection()).run();
    }

    public void run() throws SQLException {
        try(Connection connection = createConnection()) {
            this.connection = connection;

            int id = insert("Camila Gaertner");
            update(id, "Camila Gaertnerrr");
            printList();
            delete(id);

            id = insert("Camila Gaertner'); CREATE TABLE IF NOT EXISTS injection (field int); -- ");
            delete(id);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17?allowMultiQueries=true", "root", null);
    }

    public int insert(String login) throws SQLException {
        String sql = "INSERT INTO Login(login) VALUES('" + login + "')";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try(ResultSet generatedKeys = statement.getGeneratedKeys()) {
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        }
    }

    public void update(int id, String login) throws SQLException {
        String sql = "UPDATE Login SET login = '" + login + "' WHERE id = " + id;
        try(
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Login WHERE id = " + id;
        try(
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
        }
    }

    public void printList() throws SQLException {
        String sql = "SELECT id, login, senha FROM Login";
        try(
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
            System.out.println("Printing Login"); //Imprime o número da linha

            resultSet.beforeFirst();
            while (resultSet.next()) {
                int rowNumber = resultSet.getRow(); //Obtem o número da linha
                int id = resultSet.getInt("id"); //Obtem o valor da coluna id
                String login = resultSet.getNString("Login"); //Obtem o valor da coluna login
                String senha = resultSet.getNString("Login"); //Obtem o valor da coluna senha

                System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
                System.out.println("Printing row #" + rowNumber); //Imprime o número da linha
                System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

                System.out.println("Id    : " + id); //Imprime o id
                System.out.println("login : " + login); //Imprime o login
                System.out.println("senha : " + senha); //Imprime a senha
            }
        }
    }
}
