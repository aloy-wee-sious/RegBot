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

    public static ArrayList<User> addme(ArrayList<User> users, ArrayList<User> pending, long id){
        if(users.contains(id) || pending.contains(id)){

        }
        return pending;
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
