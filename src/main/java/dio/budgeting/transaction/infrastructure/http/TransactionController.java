package dio.budgeting.transaction.infrastructure.http;

import dio.budgeting.transaction.application.ListTransactionByTypeUseCase;
import dio.budgeting.transaction.application.PersistTransactionUseCase;
import dio.budgeting.transaction.domain.TypeTransaction;
import dio.budgeting.transaction.infrastructure.http.request.TransactionRequest;
import dio.budgeting.transaction.infrastructure.http.response.TransactionResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping("/transactions")
@RestController
public class TransactionController {


    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByTypeUseCase listTransactionByTypeUseCase;
    private final OllamaChatModel ollamaChatModel;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase, ListTransactionByTypeUseCase listTransactionByTypeUseCase, OllamaChatModel ollamaChatModel) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByTypeUseCase = listTransactionByTypeUseCase;
        this.ollamaChatModel = ollamaChatModel;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request,
                                                 JwtAuthenticationToken token){

        var transaction = persistTransactionUseCase.execute(request.toInput(), UUID.fromString(token.getName()));
        return TransactionResponse.from(transaction);


    }

    @GetMapping("/{type}")
    public List<TransactionResponse> readTransactions (@PathVariable TypeTransaction type){
        var transactions = listTransactionByTypeUseCase.execute(type);

        return transactions
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }

    @PostMapping("/ai")
    public String transcribe(@RequestParam("file") MultipartFile file){
        var resource = file.getResource();

        var media = Media.builder()
                .mimeType(MimeTypeUtils.parseMimeType("audio/wav"))
                .data(resource)
                .build();


        var userMessage = UserMessage.builder()
                .text("""
                        Transcreva o áudio.
                        O áudio está em português brasileiro.
                        O áudio contém uma descrição de gastos ou compras financeiras.
                        Retorne somente a transcrição.
                        """)
                .media(media)
                .build();

        var prompt = new Prompt(userMessage);

        var response = ollamaChatModel.call(prompt)
                .getResult()
                .getOutput()
                .getText();

        return response;

    }
}
