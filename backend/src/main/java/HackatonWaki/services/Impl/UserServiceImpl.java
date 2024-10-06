package HackatonWaki.services.Impl;

import HackatonWaki.models.User;
import HackatonWaki.repositorys.IGenericRepository;
import HackatonWaki.repositorys.IUserRepository;
import HackatonWaki.services.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements IUserService {

   private IUserRepository repo;

    @Override
    protected IGenericRepository<User, Long> getRepo() {
        return repo;
    }
}
