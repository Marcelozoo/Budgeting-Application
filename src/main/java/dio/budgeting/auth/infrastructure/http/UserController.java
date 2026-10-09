package dio.budgeting.auth.infrastructure.http;


import dio.budgeting.auth.application.CreateUserUseCase;
import dio.budgeting.auth.application.ListUsersUseCase;
import dio.budgeting.auth.infrastructure.http.request.CreateUser;
import dio.budgeting.auth.infrastructure.http.response.UsersResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final ListUsersUseCase listUsersUseCase;
    private final CreateUserUseCase createUse;

    public UserController(ListUsersUseCase listUsersUseCase, CreateUserUseCase createUse) {
        this.listUsersUseCase = listUsersUseCase;
        this.createUse = createUse;
    }


    @Transactional
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void newUser(@RequestBody  CreateUser request){
        createUse.execute(request.toInput());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<UsersResponse> listUsers(){

        return this.listUsersUseCase.execute()
                .stream()
                .map(UsersResponse::from)
                .toList();
    }


}
