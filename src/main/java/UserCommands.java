import java.util.ArrayList;

/**
 * Created by aloysius on 9/20/16.
 */
public class UserCommands {

    private final String COMMAND_ADD = "/add";
    private final String COMMAND_VIEW = "/view";
    private final String COMMAND_DELETE = "/delete";
    private final String COMMAND_HELP = "/help";
    private final String COMMAND_ADDME = "/addme";

    public static ArrayList<User> add(ArrayList<User> users, long id, String text){
        users.get(findUser(users,id)).addRequest(text);
        return users;
    }

    public static String view(ArrayList<User> users, long id){
        return users.get(findUser(users,id)).toString();
    }

    public static ArrayList<User> delete(ArrayList<User> users, long id, int num){
        users.get(findUser(users,id)).deleteRequest(num);
        return users;
    }

    public static ArrayList<User> addme(ArrayList<User> users, ArrayList<User> pending, long id, String firstName, String lastName){
        //FIXME should create an user already exist in list exception
        if(!users.contains(id) && !pending.contains(id)){
            pending.add(new User(firstName, lastName, id));
        }
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
                        "The current admin is " + AdminCommands.getName() + "\n Or for existing user, use /help to see the list of commands";
        return result;
    }

    private static int findUser(ArrayList<User> users, long id){
        for(int i =0; i<users.size();i++){
            if(users.get(i).getUserId() == id){
                return i;
            }
        }
        return -1;
    }
}
