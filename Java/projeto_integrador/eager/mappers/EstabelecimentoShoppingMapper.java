package projeto_integrador.eager.mappers;

import projeto_integrador.eager.domain.EstabelecimentoShopping;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstabelecimentoShoppingMapper implements Mapper<EstabelecimentoShopping> {

    private Connection connection;
    private Map<Integer, EstabelecimentoShopping> loadedEstabelecimentoShoppings;

    public EstabelecimentoShoppingMapper(Connection connection) {
        this.connection = connection;
        loadedEstabelecimentoShoppings = new HashMap<>();
    }

    @Override
    public EstabelecimentoShopping findById(int id) throws SQLException {
        EstabelecimentoShopping estabelecimentoShopping = loadedEstabelecimentoShoppings.get(id);
        if (estabelecimentoShopping == null) {
            String sql = "SELECT id, id_estabelecimento, id_shopping FROM estabelecimento_shopping WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        estabelecimentoShopping = map(resultSet);
                    }
                }
            }
        }
        return estabelecimentoShopping;
    }

    @Override
    public void insert(EstabelecimentoShopping entity) throws SQLException {
        String sql = "INSERT INTO estabelecimento_shopping(id_estabelecimento, id_shopping) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getHexa());
            statement.setString(2, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedEstabelecimentoShoppings.put(id, entity);
            }
        }
    }

    @Override
    public void update(EstabelecimentoShopping entity) throws SQLException {
        String sql = "UPDATE estabelecimento_shopping SET id_estabelecimento = ?, id_shopping = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getHexa());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(EstabelecimentoShopping entity) throws SQLException {
        String sql = "DELETE FROM estabelecimento_shopping  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedEstabelecimentoShoppings.remove(entity.getId());
        }
    }

    @Override
    public List<EstabelecimentoShopping> findAll() throws SQLException {
        List<EstabelecimentoShopping> all = new ArrayList<>();
        String sql = "SELECT id, id_estabelecimento, id_shopping FROM estabelecimento_shopping";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                EstabelecimentoShopping estabelecimentoShopping = loadedEstabelecimentoShoppings.get(id);
                if (estabelecimentoShopping == null) {
                    estabelecimentoShopping = map(resultSet);
                }
                all.add(estabelecimentoShopping);
            }
        }
        return all;
    }

    private EstabelecimentoShopping map(ResultSet resultSet) throws SQLException {
        EstabelecimentoShopping estabelecimentoShopping = new EstabelecimentoShopping();
        estabelecimentoShopping.setId(resultSet.getInt(1));
        estabelecimentoShopping.setHexa(resultSet.getString(2));
        estabelecimentoShopping.setDescription(resultSet.getString(3));
        loadedEstabelecimentoShoppings.put(estabelecimentoShopping.getId(), estabelecimentoShopping);
        return estabelecimentoShopping;
    }
}
