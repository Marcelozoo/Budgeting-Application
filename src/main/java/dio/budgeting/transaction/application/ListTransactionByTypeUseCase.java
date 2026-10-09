package dio.budgeting.transaction.application;


import dio.budgeting.transaction.application.output.TransactionOutput;
import dio.budgeting.transaction.domain.TransactionRepository;
import dio.budgeting.transaction.domain.TypeTransaction;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionByTypeUseCase {

    private final TransactionRepository transactionRepository;

    public ListTransactionByTypeUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute(TypeTransaction type){

        return transactionRepository.findAllByType(type)
                .stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
