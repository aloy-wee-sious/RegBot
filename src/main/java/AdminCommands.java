import java.util.ArrayList;

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
    private static long adminId = 226481140;
    private static String name = "Aloysius";

    public static String viewUsers(ArrayList<User> users){
        String result = "";
        int count =1;
        for(User user: users){
            result = result + count + ". " + user.getName() + "\n";
            count++;
        }
        return result;
    }

    public static boolean isAdmin(Integer id) {
        return id == adminId;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        AdminCommands.name = name;
    }

    public static long getAdminId() {
        return adminId;
    }

    public static void setAdminId(long adminId) {
        AdminCommands.adminId = adminId;
    }
}
