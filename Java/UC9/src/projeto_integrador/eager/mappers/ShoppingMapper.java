package projeto_integrador.eager.mappers;

import projeto_integrador.eager.domain.Shopping;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingMapper implements Mapper<Shopping> {

    private Connection connection;
    private Map<Integer, Shopping> loadedShoppings;

    public ShoppingMapper(Connection connection) {
        this.connection = connection;
        loadedShoppings = new HashMap<>();
    }

    @Override
    public Shopping findById(int id) throws SQLException {
        Shopping shopping = loadedShoppings.get(id);
        if (shopping == null) {
            String sql = "SELECT id, nome FROM Shopping WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        shopping = map(resultSet);
                    }
                }
            }
        }
        return shopping;
    }

    @Override
    public void insert(Shopping entity) throws SQLException {
        String sql = "INSERT INTO Shopping(nome) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedShoppings.put(id, entity);
            }
        }
    }

    @Override
    public void update(Shopping entity) throws SQLException {
        String sql = "UPDATE Shopping SET nome = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Shopping entity) throws SQLException {
        String sql = "DELETE FROM Shopping  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedShoppings.remove(entity.getId());
        }
    }

    @Override
    public List<Shopping> findAll() throws SQLException {
        List<Shopping> all = new ArrayList<>();
        String sql = "SELECT id, nome FROM Shopping";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Shopping shopping = loadedShoppings.get(id);
                if (shopping == null) {
                    shopping = map(resultSet);
                }
                all.add(shopping);
            }
        }
        return all;
    }

    private Shopping map(ResultSet resultSet) throws SQLException {
        Shopping shopping = new Shopping();
        shopping.setId(resultSet.getInt(1));
        shopping.setDescription(resultSet.getString(2));
        loadedShoppings.put(shopping.getId(), shopping);
        return shopping;
    }
}
