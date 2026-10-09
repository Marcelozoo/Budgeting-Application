package dio.budgeting.auth.infrastructure.http.request;

import dio.budgeting.auth.application.input.UserInput;
import dio.budgeting.transaction.application.input.PersistTransactionInput;

public record LoginRequest(String username, String password) {

    public  UserInput toInput() {
        return new UserInput(username, password);
    }
}
