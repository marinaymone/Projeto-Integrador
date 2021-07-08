package projeto_integrador.eager;


import projeto_integrador.eager.domain.*;
import projeto_integrador.eager.mappers.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

//@SuppressWarnings("unchecked")
public class MapperTest {

    private Connection connection;
    public void test()  {
        try (Connection connection = createConnection()) {
            this.connection = connection;
            testCategoria();
            testEstabelecimentoShopping();
            testFavoritos();
            testLogin();
            testShopping();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testCategoria() throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setNome("Teste");

        CategoriaMapper categoriaMapper = new CategoriaMapper(connection);
        categoriaMapper.insert(categoria);
        categoria.setNome("Henrique Prof");
        categoriaMapper.update(categoria);
        categoriaMapper.delete(categoria);
        categoria = categoriaMapper.findById(1);
        assertTrue(categoria.getNome().equals("Super Mercado"),"Erro: Descrição diferente de 'Super Mercado'");
        List<Categoria> categoriaList = categoriaMapper.findAll();
        assertTrue(categoriaList.categoria() == 4, "Erro: Mais de 4 tipos");
        System.out.println("Aprovado testCategoria!");

    }

    private void testShopping() throws SQLException {
        Shopping shopping = new Shopping();
        shopping.setNome("Teste!!!");

        ShoppingMapper shoppingMapper = new ShoppingMapper(connection);
        shoppingMapper.insert(shopping);
        shopping.setNome("Teste!!!");
        shoppingMapper.update(shopping);
        shoppingMapper.delete(shopping);
        shopping = shopping.findById(1);
        assertTrue(shopping.getNome().equals("Bourbon Shopping Canoas"),"Erro: Descrição diferente de 'Bourbon Shopping Canoas'");
        List<Shopping> shoppingList = shoppingMapper.findAll();
        assertTrue(shoppingList.shopping() == 4,"Erro: Mais de 4 tipos");
        System.out.println("Passed testShopping!");
    }

    private void testEstabelecimentoShopping() throws SQLException {
        EstabelecimentoShopping estabelecimentoShopping = new EstabelecimentoShopping();
        estabelecimentoShopping.setId_estabelecimento("Teste!!!");
        estabelecimentoShopping.setId_shopping("5");
        EstabelecimentoShoppingMapper estabelecimentoShoppingMapper = new EstabelecimentoShoppingMapper(connection);

        estabelecimentoShoppingMapper.insert(estabelecimentoShopping);
        estabelecimentoShopping.setId_estabelecimento("Teste!!!");
        estabelecimentoShoppingMapper.update(estabelecimentoShopping);
        estabelecimentoShoppingMapper.delete(estabelecimentoShopping);
        estabelecimentoShopping = estabelecimentoShoppingMapper.findById(1);

        assertTrue(estabelecimentoShopping.getId_estabelecimento().equals(5),"Erro: Descrição diferente de '5'");
        assertTrue(estabelecimentoShopping.getId_shopping().equals(6),"Erro: Descrição diferente de '6'");
        List<EstabelecimentoShopping> estabelecimentoShoppingList = estabelecimentoShoppingMapper.findAll();
        assertTrue(estabelecimentoShoppingList.size() == 4,"Erro: Mais de 4 tipos");
        System.out.println("Passed testEstabelecimentoShopping!");
    }

    private void testFavoritos() throws SQLException {
        Favoritos favoritos = new Favoritos();
        favoritos.setId_estabelecimento("Teste!!!");
        favoritos.setId_Login("Teste!!!");
        FavoritosMapper favoritosMapper = new FavoritosMapper(connection);

        favoritosMapper.insert(favoritos);
        favoritos.setId_estabelecimento("Teste!!!");
        favoritos.setId_Login("Teste!!!");
        favoritosMapper.update(favoritos);
        favoritosMapper.delete(favoritos);
        favoritos = favoritosMapper.findById(1);
        assertTrue(favoritos.getId_estabelecimento().equals(4),"Erro: Descrição diferente de '4'");
        assertTrue(favoritos.getId_shopping().equals(4),"Erro: Descrição diferente de '5'");

        List<Favoritos> favoritosList = favoritosMapper.findAll();
        assertTrue(favoritosList.favoritos() == 3,"Erro: Mais de 3 tipos");
        System.out.println("Passed testFavoritos!");
    }

    private void testLogin() throws SQLException {
        CategoriaMapper categoriaMapper = new CategoriaMapper (connection);
        EstabelecimentoShoppingMapper estabelecimentoShoppingMapper = new EstabelecimentoShoppingMapper (connection);
        FavoritosMapper favoritosMapper = new FavoritosMapper (connection);
        ShoppingMapper shoppingMapper = new ShoppingMapper (connection);

        LoginMapper loginMapper = new LoginMapper (connection,categoriaMapper,estabelecimentoShoppingMapper,favoritosMapper,shoppingMapper);

        Categoria categoria = new Categoria();
        categoria.setNome("Teste!!!");
        categoriaMapper.insert(categoria);

        EstabelecimentoShopping estabelecimentoShopping = new EstabelecimentoShopping();
        estabelecimentoShopping.setId_estabelecimento("Teste!!!");
        estabelecimentoShopping.setId_shopping("Teste!!!");
        estabelecimentoShoppingMapper.insert(estabelecimentoShopping);

        Favoritos favoritos = new Favoritos();
        favoritos.setId_estabelecimento("Teste!!!");
        favoritos.setId_login("Teste!!!");
        favoritosMapper.insert(favoritos);

        Shopping shopping = new Shopping();
        shopping.setNome("Teste!!!");
        shoppingMapper.insert(shopping);

        Login login = new Login();
        login.setCategoria(categoria);
        login.setEstabelecimentoShopping(estabelecimentoShopping);
        login.setShopping(shopping);
        login.setFavoritos(favoritos);

        loginMapper.insert(login);
        loginMapper.update(login);
        loginMapper.delete(login);

        categoriaMapper.delete(categoria);
        estabelecimentoShoppingMapper.delete(estabelecimentoShopping);
        shoppingMapper.delete(shopping);
        favoritosMapper.delete(favoritos);

        Login = LoginMapper.findById(1);
        assertTrue(Login.getPrice() == 250,"Error!!!!");

        List<Login> LoginList = LoginMapper.findAll();
        assertTrue(LoginList.size() == 192,"Error!!!!");
        System.out.println("Passed testLOGIN!");

    }

    private Connection createConnection() throws SQLException, IOException {
        InputStream dbConnectionStream = MapperTest.class.getClassLoader().getResourceAsStream("dbconnection.properties");
        Properties properties = new Properties();
        properties.load(dbConnectionStream);
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    private void assertTrue(boolean expected, String message) {
        if(!expected) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        (new MapperTest()).test();
    }
}
