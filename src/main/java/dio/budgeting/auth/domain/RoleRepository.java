package dio.budgeting.auth.domain;

import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;

import javax.management.relation.Role;

public interface RoleRepository {

    RoleEntity findByName(String name);
}
