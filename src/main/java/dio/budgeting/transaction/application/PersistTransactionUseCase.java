package dio.budgeting.transaction.application;


import dio.budgeting.transaction.application.input.PersistTransactionInput;
import dio.budgeting.transaction.application.output.TransactionOutput;
import dio.budgeting.transaction.domain.Transaction;
import dio.budgeting.transaction.domain.TransactionId;
import dio.budgeting.transaction.domain.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersistTransactionUseCase {


    private final TransactionRepository transactionRepository;


    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

    }

    public TransactionOutput execute(PersistTransactionInput input, UUID userId){

        var transaction = transactionRepository.save(new Transaction(
                new TransactionId(),
                input.description(),
                input.type(),
                userId
        ));


        return TransactionOutput.from(transaction);
    }
}
