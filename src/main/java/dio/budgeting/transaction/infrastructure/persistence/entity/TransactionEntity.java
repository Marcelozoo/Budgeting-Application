package dio.budgeting.transaction.infrastructure.persistence.entity;


import dio.budgeting.auth.infrastructure.persistence.entity.UserEntity;
import dio.budgeting.transaction.domain.Transaction;
import dio.budgeting.transaction.domain.TransactionId;
import dio.budgeting.transaction.domain.TypeTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_transaction")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private UUID id;

    @Column(length = 100, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction type;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;


    public static TransactionEntity from (Transaction transaction, UserEntity user){

        return new TransactionEntity(
                transaction.getId().id(),
                transaction.getDescription(),
                transaction.getType(),
                user
        );

    }

    public Transaction toDomain(){
        return new Transaction(
                new TransactionId(),
                this.description,
                this.getType(),
                user.getId()

        );
    }


}

