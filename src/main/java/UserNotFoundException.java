/**
 * Created by aloysius on 11/15/16.
 */
public class UserNotFoundException extends Exception {
    private String message;

    public UserNotFoundException() {}

    public UserNotFoundException(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
