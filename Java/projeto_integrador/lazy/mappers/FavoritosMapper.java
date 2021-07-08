package projeto_integrador.eager.mappers;

import projeto_integrador.eager.domain.Favoritos;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritosMapper implements Mapper<Favoritos> {

    private Connection connection;
    private Map<Integer, Favoritos> loadedFavoritos;

    public FavoritosMapper(Connection connection) {
        this.connection = connection;
        loadedFavoritos = new HashMap<>();
    }

    @Override
    public Favoritos findById(int id) throws SQLException {
        Favoritos favoritos = loadedFavoritos.get(id);
        if (favoritos == null) {
            String sql = "SELECT id, id_estabelecimento, id_login FROM Favoritos WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.first()) {
                        favoritos = map(resultSet);
                    }
                }
            }
        }
        return favoritos;
    }

    @Override
    public void insert(Favoritos entity) throws SQLException {
        String sql = "INSERT INTO Favoritos(id_estabelecimento,id_login) VALUES(?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getSigla());
            statement.setString(2, entity.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                entity.setId(id);
                loadedFavoritos.put(id, entity);
            }
        }
    }

    @Override
    public void update(Favoritos entity) throws SQLException {
        String sql = "UPDATE Favoritos SET id_estabelecimento = ?, id_login = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getSigla());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Favoritos entity) throws SQLException {
        String sql = "DELETE FROM Favoritos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.executeUpdate();
            loadedFavoritos.remove(entity.getId());
        }
    }

    @Override
    public List<Favoritos> findAll() throws SQLException {
        List<Favoritos> all = new ArrayList<>();
        String sql = "SELECT id, id_estabelecimento, id_login FROM Tamanho";
        try (
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Favoritos favoritos = loadedFavoritos.get(id);
                if (favoritos == null) {
                    favoritos = map(resultSet);
                }
                all.add(favoritos);
            }
        }
        return all;
    }

    private Favoritos map(ResultSet resultSet) throws SQLException {
        Favoritos favoritos = new Favoritos();
        favoritos.setId(resultSet.getInt(1));
        favoritos.setSigla(resultSet.getString(2));
        favoritos.setDescription(resultSet.getString(3));
        loadedFavoritos.put(favoritos.getId(), favoritos);
        return favoritos;
    }
}
