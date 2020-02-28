package demos.springdata.springdataintro.exception;

public class IllegalBankOperationException extends RuntimeException {

    public IllegalBankOperationException() {
    }

    public IllegalBankOperationException(String message) {
        super(message);
    }

    public IllegalBankOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalBankOperationException(Throwable cause) {
        super(cause);
    }
}
