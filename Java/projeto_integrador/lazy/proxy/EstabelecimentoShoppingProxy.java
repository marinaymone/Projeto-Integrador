package projeto_integrador.lazy.proxy;

import projeto_integrador.lazy.domain.EstabelecimentoShopping;
import projeto_integrador.lazy.mappers.Mapper;

public class EstabelecimentoShoppingProxy extends EstabelecimentoShopping {
    private Mapper<Color> mapper;

    public EstabelecimentoShoppingProxy(int id, Mapper<EstabelecimentoShopping> mapper) {
        setId(id);
        this.mapper = mapper;
    }
}
