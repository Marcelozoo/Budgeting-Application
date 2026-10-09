package dio.budgeting.transaction.application.output;

import dio.budgeting.transaction.domain.Transaction;

public record TransactionOutput(String id, String description, String type, String idUser) {


    public static TransactionOutput from (Transaction transaction){
        return new TransactionOutput(
                transaction.getId().id().toString(),
                transaction.getDescription(),
                transaction.getType().name(),
                transaction.getUserId().toString()
        );
    }
}
