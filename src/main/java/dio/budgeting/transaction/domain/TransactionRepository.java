package dio.budgeting.transaction.domain;

import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);
    List<Transaction> findAllByType(TypeTransaction type);
}
