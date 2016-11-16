import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aloysius on 9/21/16.
 */
public class AdminCommands {

    private static ArrayList<User> admins = new ArrayList<>();
    private static String reggyquote = "HI HI! :)\n" +
            "Please submit your prayer requests here!! Feel free to PM me directly if it's more convenient! Have a great week ahead:)";

    public static String viewUsers(ArrayList<User> users) {
        if (users.isEmpty()) {
            return "Empty";
        }
        return printUsers(users);
    }

    private static String printUsers(ArrayList<User> users) {
        String result = "";
        int count = 1;
        for(User user: users){
            result = result + count + ". " + user.getName() + "\n";
            count++;
        }
        return result;
    }

    public static boolean isAdmin(long id) {
        for (User admin : admins) {
            if (admin.getUserId() == id) {
                return true;
            }
        }
        return false;
    }

    public static String getAdminsName() {
        String result ="";
        for(User user: admins){
            result = result + user.getName() + " ";
        }
        return result;
    }

    public static void addAdmin(ArrayList<User> user, int i) throws InvalidParameterException {
        if(!admins.contains(user.get(i-1))) {
            admins.add(user.get(i - 1));
        } else {
            throw new InvalidParameterException("Users is already an admin");
        }
    }

    public static ArrayList<User> removeUser(ArrayList<User> users, int i) throws InvalidParameterException {

        if (users.size() < i) {
            throw new InvalidParameterException("The number " + i + " is not valid");
        }

        for(int j = 0; j < admins.size(); j++){
            if(admins.get(j).getUserId() == users.get(i-1).getUserId()){
                admins.remove(j);
                break;
            }
        }
        users.remove(i - 1);
        return users;
    }

    public static String viewPending(ArrayList<User> users) {
        if (users.isEmpty()) {
            return "Empty";
        }
        return printUsers(users);
    }

    public static ArrayList<User> addUser(ArrayList<User> myUsers, ArrayList<User> seekApproval, int i) throws InvalidParameterException {
        try {
            myUsers.add(seekApproval.get(i - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidParameterException("The number " + i + "is not valid");
        }
        return myUsers;
    }

    public static ArrayList<User> removePending(ArrayList<User> seekApproval, int i) throws InvalidParameterException {
        try {
            seekApproval.remove(i - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidParameterException("The number " + i + "is not valid");
        }
        return seekApproval;
    }

    public static void setAdmins(ArrayList<User> admins) {
        AdminCommands.admins = admins;
    }

    public static ArrayList<User> getAdmins() {
        return admins;
    }

    public static ArrayList<User> remind(ArrayList<User> users){
        ArrayList<User> remindUsers = new ArrayList<>();
        for(User user: users){
            if(!user.haveRequest()){
                remindUsers.add(user);
            }
        }
        return remindUsers;
    }

    public static String newWeek(){
        //TODO new week
        return "";
    }

    public static String publish(){
        //TODO pdf library
        return "To-be-done";
    }

    public static String help(){
        return "Hello admin below are the list of commands available\n" +
                "/viewusers to view all existing users\n" +
                "/approve to add users from pending users\n" +
                "/remove to remove existing users\n" +
                "/viewpending to view pending user\n" +
                "/removepending to remove users from pending\n" +
                "/remind to remind users\n" +
                "/publish to publish\n" +
                "/newadmin to add user to admin\n"+
                "/adminhelp to bring up help manual";


    }

    public static String viewRequest(ArrayList<User> myUsers) {
        return myUsers.toString();
    }

    public static void addDefault(){
        admins.add(new User("Aloysius", null, Long.parseLong("226481140")));
    }
}
