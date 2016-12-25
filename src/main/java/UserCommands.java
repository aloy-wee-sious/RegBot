import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by aloysius on 9/20/16.
 */
public class UserCommands {

    public static ArrayList<User> add(ArrayList<User> users, long id, String text) throws UserNotFoundException {
        users.get(findUser(users,id)).addRequest(text);
        return users;
    }

    public static String view(ArrayList<User> users, long id) throws UserNotFoundException {
        return users.get(findUser(users,id)).toString();
    }

    public static ArrayList<User> delete(ArrayList<User> users, long id, String text) throws UserNotFoundException, InvalidParameterException {
        int num;
        try {
            num = Integer.parseInt(text);
        }catch (NumberFormatException e) {
            throw new InvalidParameterException("Must be a number and not empty");
        }
        users.get(findUser(users,id)).deleteRequest(num);
        return users;
    }

    public static ArrayList<User> addme(ArrayList<User> users, ArrayList<User> pending, User user) throws AlreadyExistException {
        if (pending.contains(user)) {
            throw new AlreadyExistException("Already exist in pending list");
        }

        if (users.contains(user)) {
            throw new AlreadyExistException("Already been approved");
        }
        pending.add(user);
        return pending;
    }

    public static String help(){

        String result = "Here's a list of what RegBot can do for you:\n" +
                        "/addme to request access permission from admin\n"+
                        "/add (request) to submit a request\n" +
                        "/delete (request number) to delete a request\n" +
                        "/view to see all request\n" +
                        "/help to bring up this menu\n\n" +
                        "If you're an admin and need help, use /adminhelp!\n";
        return result;
    }

    public static String start(){
        String result = "Hi welcome to RegBot! If your are a new user, use /addme to request access permission from admin\n" +
                        "The current admin is " + AdminCommands.getAdminsName() +
                        "\nOr for existing user, use /help to see the list of commands";
        return result;
    }

    private static int findUser(ArrayList<User> users, long id) throws UserNotFoundException {
        for(int i =0; i<users.size();i++){
            if(users.get(i).getUserId() == id){
                return i;
            }
        }
        throw new UserNotFoundException("User not found");
    }

    public static ArrayList<User> newWeek(ArrayList<User> users) {
        for(User u : users){
            u.clearRequest();
        }
        return users;
    }
}
