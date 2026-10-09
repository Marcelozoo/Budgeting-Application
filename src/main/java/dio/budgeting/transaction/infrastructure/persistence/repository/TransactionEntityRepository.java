package dio.budgeting.transaction.infrastructure.persistence.repository;

import dio.budgeting.transaction.domain.TypeTransaction;
import dio.budgeting.transaction.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByType(TypeTransaction type);
}
