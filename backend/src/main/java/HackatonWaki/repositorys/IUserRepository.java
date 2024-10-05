package HackatonWaki.repositorys;

import HackatonWaki.models.User;

import java.util.Optional;

public interface IUserRepository extends IGenericRepository<User, Long>{

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
