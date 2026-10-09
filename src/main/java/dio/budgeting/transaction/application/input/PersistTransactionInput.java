package dio.budgeting.transaction.application.input;

import dio.budgeting.transaction.domain.TypeTransaction;

import java.util.UUID;

public record PersistTransactionInput(String description, TypeTransaction type, UUID id) {
}
