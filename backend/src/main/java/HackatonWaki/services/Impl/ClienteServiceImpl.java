package HackatonWaki.services.Impl;

import HackatonWaki.enums.Rol;
import HackatonWaki.exceptions.AplicationExceptions;
import HackatonWaki.models.Cliente;
import HackatonWaki.models.User;
import HackatonWaki.repositorys.IClienteRepository;
import HackatonWaki.repositorys.IGenericRepository;
import HackatonWaki.repositorys.IUserRepository;
import HackatonWaki.services.IClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl extends GenericServiceImpl<Cliente, Long> implements IClienteService {

    private final IClienteRepository repo;
    private final IUserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClienteServiceImpl(IUserRepository userRepo, IClienteRepository repo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.repo = repo;
        this.passwordEncoder = (BCryptPasswordEncoder) passwordEncoder;
    }

    @Override
    protected IGenericRepository<Cliente, Long> getRepo() {
        return repo;
    }

    @Override
    public Cliente save(Cliente cliente) {
        try {
            if (userRepo.existsByEmail(cliente.getUsuario().getUsername())) {
                throw new AplicationExceptions("Email ya existente: " + cliente.getUsuario().getEmail());
            }
            String encodedPassword = passwordEncoder.encode(cliente.getUsuario().getPassword());
            cliente.getUsuario().setPassword(encodedPassword);
            cliente.getUsuario().setRol(Rol.CLIENTE);
            //Si queremos implementar un Borrado Lógico
            //cliente.setActivo(true);
            User savedUser = userRepo.save(cliente.getUsuario());
            cliente.setUsuario(savedUser);
            return repo.save(cliente);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public Page<Cliente> findAllPageable(Pageable pageable) {
        //El findAll del JpaRepository tambien maneja Pageables
        return repo.findAll(pageable);
    }

    @Override
    public Page<Cliente> getFilteredClientes(String nombre, String apellido, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (nombre != null && apellido != null) {
            return repo.findByNombreAndApellido(nombre, apellido, pageable);
        } else if (nombre != null) {
            return repo.findByNombre(nombre, pageable);
        } else if (apellido != null) {
            return repo.findByApellido(apellido, pageable);
        } else {
            return repo.findAll(pageable);
        }
    }
}
