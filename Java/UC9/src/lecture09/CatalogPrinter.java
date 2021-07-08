package lecture09;

import java.sql.*;

public class LoginPrinter {

    private String loginSQL = "SELECT id, login, senha FROM Login ORDER BY id";

    public static void main(String[] args) throws SQLException {
        (new LoginPrinter()).run();
    }

    public void run() throws SQLException {
        try(    //Efetua a conexão
                Connection connection = createConnection();
                //Cria o objeto de declaração de expressão
                Statement statement = createStatement(connection);
                //Executa uma expressão SQL e obtem seu resultado
                ResultSet loginResultSet = createResultSet(statement, loginSQL);
        ) {

            //Imprime o resultado da expressão SQL
            printLoginResultSet(loginResultSet);
        }
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost/pi17", "root", null);
    }

    private Statement createStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    private ResultSet createResultSet(Statement statement, String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    private void printLoginResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
        System.out.println("Printing Login"); //Imprime o número da linha
        System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

        resultSet.beforeFirst();
        while (resultSet.next()) {
            int rowNumber = resultSet.getRow(); //Obtem o número da linha
            int id = resultSet.getInt("id"); //Obtem o valor da coluna id
            String login = resultSet.getNString("Login"); //Obtem o valor da coluna Login
            String senha = resultSet.getNString("Senha"); //Obtem o valor da coluna Senha

            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes
            System.out.println("Printing row #" + rowNumber); //Imprime o número da linha
            System.out.println("-".repeat(30)); //Imprime o caractere '-' 30 vezes

            System.out.println("Id    : " + id); //Imprime o id
            System.out.println("Login : " + login); //Imprime o Login
            System.out.println("Senha : " + senha); //Imprime a Senha
        }
    }

    private void printloginResultSet(ResultSet resultSet) throws SQLException {

    }
}
