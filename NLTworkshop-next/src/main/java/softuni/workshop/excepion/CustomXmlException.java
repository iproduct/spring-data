package softuni.workshop.excepion;

public class CustomXmlException extends  RuntimeException{
    public CustomXmlException() {
    }

    public CustomXmlException(String message) {
        super(message);
    }

    public CustomXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomXmlException(Throwable cause) {
        super(cause);
    }
}
