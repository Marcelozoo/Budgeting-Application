package dio.budgeting.auth.infrastructure.persistence.repository;

import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleEntityRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);

}
