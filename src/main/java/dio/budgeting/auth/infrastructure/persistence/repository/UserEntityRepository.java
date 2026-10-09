package dio.budgeting.auth.infrastructure.persistence.repository;

import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);
}
