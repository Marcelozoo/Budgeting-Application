package dio.budgeting.transaction.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;


@Getter
@AllArgsConstructor
public class Transaction {

    private TransactionId id;
    private String description;
    private TypeTransaction type;
    private UUID userId;


    public Transaction(String description, TypeTransaction type, UUID userId) {
        this.id = new TransactionId();
        this.description = description;
        this.type = type;
        this.userId = userId;
    }
}
