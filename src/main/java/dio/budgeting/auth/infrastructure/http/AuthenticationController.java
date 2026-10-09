package dio.budgeting.auth.infrastructure.http;


import dio.budgeting.auth.application.AuthenticationUseCase;
import dio.budgeting.auth.infrastructure.http.request.LoginRequest;
import dio.budgeting.auth.infrastructure.http.response.LoginResponse;
import dio.budgeting.auth.infrastructure.persistence.entity.RoleEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }



    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        var token =  authenticationUseCase.execute(request.toInput());

        return LoginResponse.from(token);

    }
}
