package dio.budgeting.auth.application;

import dio.budgeting.auth.application.input.UserInput;
import dio.budgeting.auth.domain.RoleRepository;
import dio.budgeting.auth.domain.UserRepository;
import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CreateUserUseCase(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void execute(UserInput input){

        var basicRole = roleRepository.findByName(RoleEntity.Values.BASIC.name());
        var userFromDb = userRepository.findByUsername(input.username());

        if (userFromDb != null){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        };

        var user = new UserEntity();

        user.setUsername(input.username());
        user.setPassword(bCryptPasswordEncoder.encode(input.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
    }
}
