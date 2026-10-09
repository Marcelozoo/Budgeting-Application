package dio.budgeting.auth.application.output;

import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;

public record UserOutput(String username) {
    public static UserOutput from(UserEntity userEntity) {
        return new UserOutput(userEntity.getUsername());
    }
}
