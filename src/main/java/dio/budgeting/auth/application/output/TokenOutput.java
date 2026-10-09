package dio.budgeting.auth.application.output;

public record TokenOutput(String jwtValue, long expiresIn ) {
}
