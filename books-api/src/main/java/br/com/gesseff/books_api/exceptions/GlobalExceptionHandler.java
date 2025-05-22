package br.com.gesseff.books_api.exceptions;

import org.springframework.http.HttpStatus; // HttpStatus é uma enumeração do Spring que contém todos os códigos de status HTTP. como 400 para Bad Request e 500 para Internal Server Error)
import org.springframework.http.ResponseEntity; // ResponseEntity é uma classe do Spring que representa uma resposta HTTP.
import org.springframework.web.bind.MethodArgumentNotValidException; // MethodArgumentNotValidException é uma exceção lançada quando um argumento de um método anotado com @Valid falha na validação.
import org.springframework.web.bind.annotation.ExceptionHandler; // Anotação usada para indicar que um método é um manipulador de exceções.
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException; // ResponseStatusException é uma exceção lançada quando um controlador REST responde com um código de status HTTP específico.

import java.util.List;
import java.util.stream.Collectors;
//essa classe untercepta  as exceções lançadas pelos controladores REST e fornece uma resposta HTTP apropriada para o cliente.
@RestControllerAdvice // Indica que esta classe fornece tratamento global de exceções para todos os controladores REST na aplicação.
public class GlobalExceptionHandler { //

    @ExceptionHandler(ResponseStatusException.class) // Indica que este método trata exceções do tipo ResponseStatusException.
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(ex.getReason(), ex.getStatusCode()); // Retorna uma resposta HTTP com o código de status e a mensagem da exceção.
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream() //percorre a lista de erros
                .map(error -> error.getField() + ": " + error.getDefaultMessage())// transforma cada erro em uma mensagem que junta o nome do campo com a mensagem padrão do erro
                .collect(Collectors.toList()); //coleta as mensagens em uma lista

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);  //retorna a lista de mensagens de erro com o código de status HTTP 400 (Bad Request).
    }

    //esse método é executado quando qualquer exceção não tratada especificamente por outro método é lançada.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("Ocorreu um erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

