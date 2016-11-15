/**
 * Created by aloysius on 11/15/16.
 */
public class InvalidParameterException extends Exception {

    private String message;

    public InvalidParameterException() {}

    public InvalidParameterException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
