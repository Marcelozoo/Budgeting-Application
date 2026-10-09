package dio.budgeting.auth.infrastructure.security;


import dio.budgeting.auth.domain.RoleRepository;
import dio.budgeting.auth.domain.UserRepository;
import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger log = LoggerFactory.getLogger(AdminUserConfig.class);

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(RoleEntity.Values.ADMIN.name());
        var userAdmin = userRepository.findByUsername("admin");

        if(userAdmin == null){
            var user = new UserEntity();
            user.setUsername("admin");
            user.setPassword(bCryptPasswordEncoder.encode("123"));
            user.setRoles(Set.of(roleAdmin));

            userRepository.save(user);
        }else {
            log.info("Admin ja existe");
        }

    }
}
