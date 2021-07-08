package projeto_integrador.lazy.proxy;

import projeto_integrador.lazy.domain.Shopping;
import projeto_integrador.lazy.mappers.Mapper;

public class ShoppingProxy extends Shopping {
    private Mapper<Shopping> mapper;

    public ShoppingProxy(int id, Mapper<Shopping> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
