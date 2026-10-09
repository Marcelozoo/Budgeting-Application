package dio.budgeting.auth.infrastructure.persistence.repository;

import dio.budgeting.auth.domain.RoleRepository;
import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import org.springframework.stereotype.Repository;


@Repository
public class JpaRoleRepository implements RoleRepository {

    private final RoleEntityRepository roleEntityRepository;


    public JpaRoleRepository(RoleEntityRepository roleEntityRepository) {
        this.roleEntityRepository = roleEntityRepository;
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleEntityRepository.findByName(name);
    }
}
