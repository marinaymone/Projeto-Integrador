package projeto_integrador.eager.mappers;

import java.sql.SQLException;
import java.util.List;

public interface Mapper<E> {
    public E findById(int id) throws SQLException;
    public void insert(E entity) throws SQLException;
    public void update(E entity) throws SQLException;
    public void delete(E entity) throws SQLException;
    public List<E> findAll() throws SQLException;
}
