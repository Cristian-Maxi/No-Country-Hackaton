package HackatonWaki.repositorys;

import HackatonWaki.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends IGenericRepository<User, Long>{

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
