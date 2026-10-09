package dio.budgeting.auth.infrastructure.http.response;

import dio.budgeting.auth.application.output.TokenOutput;
import dio.budgeting.auth.infrastructure.http.request.LoginRequest;

public record LoginResponse(String accessToken, Long expiresIn) {

    public static LoginResponse from(TokenOutput  token){
        return new LoginResponse(token.jwtValue(), token.expiresIn());
    }
}
