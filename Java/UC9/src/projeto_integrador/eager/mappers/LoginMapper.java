package projeto_integrador.eager.mappers;

import projeto_integrador.eager.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginMapper implements Mapper<Login> {

    private Connection connection;
    private Mapper<Categoria> categoriaMapper;
    private Mapper<EstabelecimentoShopping> estabelecimentoShoppingMapper;
    private Mapper<Favoritos> favoritosMapper;
    private Mapper<Shopping> shoppingMapper;
    private Map<Integer, Login> loadedLogin;

    public LoginMapper(Connection connection, Mapper<Categoria> categoriaMapper, Mapper<EstabelecimentoShopping> estabelecimentoShoppingMapper, Mapper<Favoritos> favoritosMapper, Mapper<Shopping> shoppingMapper) {
        this.connection = connection;
        this.categoriaMapper = categoriaMapper;
        this.estabelecimentoShoppingMapper = estabelecimentoShoppingMapper;
        this.favoritosMapper = favoritosMapper;
        this.shoppingMapper = shoppingMapper;
        loadedLogin = new HashMap<>();
    }

    @Override
    public Login findById(int id) throws SQLException {
        Login login = loadedLogin.get(id);
        if (login == null) {
            String sql = "SELECT id, login, senha FROM Login WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        login = map(resultSet);
                    }
                }
            }
        }
        return login;
    }

    @Override
    public void insert(Login entity) throws SQLException {
        String sql = "INSERT INTO Login(login, senha) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getCategoria().getId());
            statement.setInt(2, entity.getEstabelecimentoShopping().getId());
            statement.setInt(3, entity.getFavoritos().getId());
            statement.setInt(4, entity.getShopping().getId());

            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedLogin.put(id, entity);
            }
        }
    }

    @Override
    public void update(Login entity) throws SQLException {
        String sql = "UPDATE Login SET nome = ?, senha = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getCategoria().getId());
            statement.setInt(2, entity.getEstabelecimento().getId());
            statement.setInt(3, entity.getFavoritos().getId());
            statement.setInt(4, entity.getShopping().getId());
            statement.setInt(7, entity.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Login entity) throws SQLException {
        String sql = "DELETE FROM Login  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedLogin.remove(entity.getId());
        }
    }

    @Override
    public List<Login> findAll() throws SQLException {
        List<Login> all = new ArrayList<>();
        String sql = "SELECT id, nome, senha FROM Login";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Login login = loadedLogin.get(id);
                if (login == null) {
                    login = map(resultSet);
                }
                all.add(login);
            }
        }
        return all;
    }

    private Login map(ResultSet resultSet) throws SQLException {
        Categoria categoria = categoriaMapper.findById(resultSet.getInt(""));
        EstabelecimentoShopping estabelecimentoShopping = estabelecimentoShoppingMapper.findById(resultSet.getInt(""));
        Favoritos favoritos = favoritosMapper.findById(resultSet.getInt(""));
        Shopping shopping = shoppingMapper.findById(resultSet.getInt(""));

        Login login = new Login();
        login.setId(resultSet.getInt("id"));
        login.setCategoria(categoria);
        login.setEstabelecimentoShopping(estabelecimentoShopping);
        login.setFavoritos(favoritos);
        login.setShopping(shopping);

        Login.put(login.getId(), login);
        return login;
    }

}
