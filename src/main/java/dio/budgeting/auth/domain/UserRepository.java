package dio.budgeting.auth.domain;

import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    UserEntity findByUsername(String username);
    List<UserEntity> findAll();
    void save(UserEntity user);
}
