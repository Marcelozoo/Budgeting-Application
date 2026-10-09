package dio.budgeting.transaction.infrastructure.http.request;

import dio.budgeting.transaction.application.input.PersistTransactionInput;
import dio.budgeting.transaction.domain.TypeTransaction;

import java.util.UUID;

public record TransactionRequest(String description, TypeTransaction type, UUID id) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, type, id);
    }
}