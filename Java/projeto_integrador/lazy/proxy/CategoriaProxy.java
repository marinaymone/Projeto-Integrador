package projeto_integrador.lazy.proxy;

import projeto_integrador.lazy.domain.Categoria;
import projeto_integrador.lazy.mappers.Mapper;

import java.sql.SQLException;

public class CategoriaProxy extends Categoria {
    private Mapper<Categoria> mapper;
    private Categoria categoria = null;

    public CategoriaProxy(int id, Mapper<Categoria> mapper) {
        setId(id);
        this.mapper = mapper;
    }

    public void setDescription(String description) {
        load();
        categoria.setDescription(description);
    }

    public String getDescription()  {
        load();
        return categoria.getDescription();
    }

    private void load() {
        if (categoria == null) {
            try {
                categoria = mapper.findById(getId());
            } catch (SQLException throwables) {
                throw new Error(throwables);
            }
        }
    }
}
