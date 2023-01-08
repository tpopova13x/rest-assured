package exceptions;

public class LicenseNotFoundException extends RuntimeException{
    public LicenseNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
