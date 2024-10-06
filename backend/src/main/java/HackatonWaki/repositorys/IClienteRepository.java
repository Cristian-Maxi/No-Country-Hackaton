package HackatonWaki.repositorys;

import HackatonWaki.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends IGenericRepository<Cliente, Long>{
    Page<Cliente> findByNombreAndApellido(String nombre, String apellido, Pageable pageable);
    Page<Cliente> findByNombre(String nombre, Pageable pageable);
    Page<Cliente> findByApellido(String apellido, Pageable pageable);
}
