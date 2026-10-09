package dio.budgeting.transaction.infrastructure.persistence.repository;

import dio.budgeting.auth.domain.UserRepository;
import dio.budgeting.auth.infrastructure.persistence.repository.UserEntityRepository;
import dio.budgeting.transaction.domain.Transaction;
import dio.budgeting.transaction.domain.TransactionRepository;
import dio.budgeting.transaction.domain.TypeTransaction;
import dio.budgeting.transaction.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class JpaTransactionRepository implements TransactionRepository {
    private final TransactionEntityRepository transactionEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository, UserEntityRepository userEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var userEntity = userEntityRepository.findById(transaction.getUserId()).orElseThrow();


        var entity = TransactionEntity.from(transaction, userEntity);

        return this.transactionEntityRepository.save(entity).toDomain();
    }

    @Override
    public List<Transaction> findAllByType(TypeTransaction type) {
        return this.transactionEntityRepository.findAllByType(type)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }
}
