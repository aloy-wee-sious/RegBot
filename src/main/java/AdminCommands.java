import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aloysius on 9/21/16.
 */
public class AdminCommands {

    private final String ADMIN_VIEW_USERS = "/adminviewusers";
    private final String ADMIN_ADD = "/adminadd";
    private final String ADMIN_REMOVE_USERS = "/adminremoveuser";
    private final String ADMIN_VIEW_PENDING = "/adminviewpending";
    private final String ADMIN_REMOVE_PENDING = "/adminremovepending";
    private final String ADMIN_VIEW_REQUEST = "/adminviewrequest";
    private final String ADMIN_VIEW_REMIND = "/adminremind";
    private final String ADMIN_PUBLISH = "/adminpublish";
    private final String ADMIN_HELP = "/adminhelp";
    private static ArrayList<User> admins = new ArrayList<>(Arrays.asList(new User("Aloysius", null, Long.parseLong("226481140"))));
    private static String reggyquote = "HI HI! :)\n" +
            "Please submit your prayer requests here!! Feel free to PM me directly if it's more convenient! Have a great week ahead:)";

    public static String viewUsers(ArrayList<User> users){
        String result = "";
        int count =1;
        for(User user: users){
            result = result + count + ". " + user.getName() + "\n";
            count++;
        }
        return result;
    }

    public static boolean isAdmin(long id) {
        for(User admin : admins){
            if(admin.getUserId() == id){
                return true;
            }
        }
        return false;
    }

    public static String getAdmins() {
        String result ="";
        for(User user: admins){
            result = result + user.getName() + " ";
        }
        return result;
    }

    public static void addAdmin(ArrayList<User> user, int i){
        if(!admins.contains(user.get(i-1))) {
            admins.add(user.get(i - 1));
        }
    }

    public static ArrayList<User> removeUser(ArrayList<User> users, int i) {
        for(int j = 0; j < admins.size(); j++){
            if(admins.get(j).getUserId() == users.get(i-1).getUserId()){
                admins.remove(j);
                break;
            }
        }
        users.remove(i-1);
        return users;
    }

    public static String viewPending(ArrayList<User> users) {
        int count = 1;
        String result = "";

        for(User user : users){
            result = result + count + ". " + user.getName() + "\n";
        }
        return result;
    }

    public static ArrayList<User> addUser(ArrayList<User> myUsers, ArrayList<User> seekApproval, int i) {
        myUsers.add(seekApproval.get(i-1));
        return myUsers;
    }

    public static ArrayList<User> removePending(ArrayList<User> seekApproval, int i) {
        seekApproval.remove(i-1);
        return seekApproval;
    }
}
