package dio.budgeting.transaction.infrastructure.http.response;

import dio.budgeting.transaction.application.output.TransactionOutput;

public record TransactionResponse(String id, String description, String type, String idUser) {

    public static TransactionResponse from(TransactionOutput output){
        return new TransactionResponse(
                output.id(),
                output.description(),
                output.type(),
                output.idUser()

        );
    }
}
