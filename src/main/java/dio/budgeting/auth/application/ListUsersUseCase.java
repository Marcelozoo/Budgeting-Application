package dio.budgeting.auth.application;

import dio.budgeting.auth.application.output.UserOutput;
import dio.budgeting.auth.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUsersUseCase {

    private final UserRepository userRepository;

    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserOutput> execute(){
        return this.userRepository.findAll()
                .stream()
                .map(UserOutput::from)
                .toList();
    }



}
