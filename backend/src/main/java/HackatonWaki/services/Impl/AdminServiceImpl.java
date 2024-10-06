package HackatonWaki.services.Impl;

import HackatonWaki.enums.Rol;
import HackatonWaki.exceptions.AplicationExceptions;
import HackatonWaki.models.Admin;
import HackatonWaki.models.User;
import HackatonWaki.repositorys.IAdminRepository;
import HackatonWaki.repositorys.IGenericRepository;
import HackatonWaki.repositorys.IUserRepository;
import HackatonWaki.services.IAdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends GenericServiceImpl<Admin, Long> implements IAdminService {

    private final IAdminRepository repo;
    private final IUserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder; // Cambiado a PasswordEncoder

    public AdminServiceImpl(IUserRepository userRepo, IAdminRepository repo, PasswordEncoder passwordEncoder) { // Cambiado a PasswordEncoder
        this.userRepo = userRepo;
        this.repo = repo;
        this.passwordEncoder = (BCryptPasswordEncoder) passwordEncoder;
    }

    @Override
    protected IGenericRepository<Admin, Long> getRepo() {
        return repo;
    }

    @Override
    public Admin save(Admin admin) {
        try {
            if (userRepo.existsByEmail(admin.getUsuario().getUsername())) {
                throw new AplicationExceptions("Usuario ya existente: " + admin.getUsuario().getUsername());
            }
            String encodedPassword = passwordEncoder.encode(admin.getUsuario().getPassword());
            admin.getUsuario().setPassword(encodedPassword);
            admin.getUsuario().setRol(Rol.ADMINISTRADOR);
            //Si queremos implementar un Borrado Lógico
            //admin.setActivo(true);
            User savedUser = userRepo.save(admin.getUsuario());
            admin.setUsuario(savedUser);
            return repo.save(admin);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions("Error al guardar el usuario: " + e.getMessage());
        }
    }

    // Si queremos implementar un Borrado Lógico
//    @Override
//    public void deleteById(Long id) {
//        Optional<Admin> admin = repo.findById(id);
//        if (admin.isPresent()) {
//            admin.get().setActivo(false);
//        }else{
//            throw new ApplicationException("Usuario no encontrado");
//        }
//    }
}
