package dio.budgeting.auth.infrastructure.http.response;

import dio.budgeting.auth.application.output.UserOutput;

public record UsersResponse(String username) {

    public static UsersResponse from (UserOutput user){
        return new UsersResponse(user.username());
    }

}
