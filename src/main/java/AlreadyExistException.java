/**
 * Created by aloysius on 11/15/16.
 */
public class AlreadyExistException extends Exception {
    private String message;

    public AlreadyExistException() {}

    public AlreadyExistException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
