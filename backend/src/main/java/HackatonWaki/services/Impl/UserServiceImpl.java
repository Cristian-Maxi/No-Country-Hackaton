package HackatonWaki.services.Impl;

import HackatonWaki.models.User;
import HackatonWaki.repositorys.IGenericRepository;
import HackatonWaki.repositorys.IUserRepository;
import HackatonWaki.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

   private final IUserRepository repo;

    @Autowired
    public UserServiceImpl(IUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User save(User user) {
        return repo.save(user);
    }

    @Override
    public User update(User user) {
        return repo.save(user);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return repo.findById(aLong);
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public void delete(Long aLong) {
        repo.deleteById(aLong);
    }
}
