package HackatonWaki.services;

import HackatonWaki.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService extends IGenericService<Cliente, Long>{

    Page<Cliente> findAllPageable(Pageable pageable);
    Page<Cliente> getFilteredClientes(String nombre, String apellido, int page, int size);
}
