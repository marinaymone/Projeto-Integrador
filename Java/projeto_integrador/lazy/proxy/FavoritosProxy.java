package projeto_integrador.lazy.proxy;

import projeto_integrador.lazy.domain.Favoritos;
import projeto_integrador.lazy.mappers.Mapper;

public class FavoritosProxy extends Favoritos {
    private Mapper<Favoritos> mapper;

    public FavoritosProxy(int id, Mapper<Favoritos> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
