package dio.budgeting.auth.infrastructure.http.request;

import dio.budgeting.auth.application.input.UserInput;

public record CreateUser(String username, String password) {

    public UserInput toInput(){
        return new UserInput(username, password);
    }
}
