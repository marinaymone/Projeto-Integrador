package projeto_integrador.eager.mappers;

import projeto_integrador.eager.domain.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaMapper implements Mapper<Categoria> {

    private Connection connection;
    private Map<Integer, Categoria> loadedCategoria;

    public CategoriaMapper(Connection connection) {
        this.connection = connection;
        loadedCategorias = new HashMap<>();
    }

    @Override
    public Categoria findById(int id) throws SQLException {
        Categoria categoria = loadedCategorias.get(id);
        if (categoria == null) {
            String sql = "SELECT id, nome FROM Categoria WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        categoria = map(resultSet);
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void insert(Categoria entity) throws SQLException {
        String sql = "INSERT INTO Categoria(nome) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedCategorias.put(id, entity);
            }
        }
    }

    @Override
    public void update(Categoria entity) throws SQLException {
        String sql = "UPDATE Categoria SET nome = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Categoria entity) throws SQLException {
        String sql = "DELETE FROM Categoria  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedCategoriads.remove(entity.getId());
        }
    }

    @Override
    public List<Categoria> findAll() throws SQLException {
        List<Categoria> all = new ArrayList<>();
        String sql = "SELECT id, nome FROM Categoria";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Categoria categoria = loadedCategorias.get(id);
                if (categoria == null) {
                    categoria = map(resultSet);
                }
                all.add(categoria);
            }
        }
        return all;
    }

    private Categoria map(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getInt(1));
        categoria.setDescription(resultSet.getString(2));
        loadedCategorias.put(categoria.getId(), categoria);
        return categoria;
    }
}
