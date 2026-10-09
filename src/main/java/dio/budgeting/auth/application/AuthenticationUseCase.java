package dio.budgeting.auth.application;

import dio.budgeting.auth.application.input.UserInput;
import dio.budgeting.auth.application.output.TokenOutput;
import dio.budgeting.auth.domain.Token;
import dio.budgeting.auth.domain.UserRepository;
import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;


@Service
public class AuthenticationUseCase {

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public AuthenticationUseCase(UserRepository userRepository, JwtEncoder jwtEncoder, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public TokenOutput execute(UserInput input){

        var user = this.userRepository.findByUsername(input.username());

        if (user == null || !user.isLoginCorrect(input, bCryptPasswordEncoder)){
            throw new BadCredentialsException("user or password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scope = user.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scope)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new TokenOutput(jwtValue, expiresIn);
    }
}
