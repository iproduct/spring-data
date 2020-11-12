package course.springdata.jsondemo.exception;

public class NonexisitingEntityException extends RuntimeException {
    public NonexisitingEntityException() {
    }

    public NonexisitingEntityException(String message) {
        super(message);
    }

    public NonexisitingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexisitingEntityException(Throwable cause) {
        super(cause);
    }
}
