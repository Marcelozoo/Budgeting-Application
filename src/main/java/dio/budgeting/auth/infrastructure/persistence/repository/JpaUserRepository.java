package dio.budgeting.auth.infrastructure.persistence.repository;

import dio.budgeting.auth.domain.UserRepository;
import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


@Repository
public class JpaUserRepository implements UserRepository {

    private final UserEntityRepository userEntityRepository;

    public JpaUserRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserEntity findByUsername(String username) {

        return userEntityRepository.findByUsername(username);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }

    @Override
    public List<UserEntity> findAll() {
        var iterable = userEntityRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .toList();
    }


   // talvez um mapper aqui apra transfsormar paraa um user domain
}
