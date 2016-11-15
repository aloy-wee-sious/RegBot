/**
 * Created by aloysius on 10/13/16.
 */
public class Parser {

    private static final String COMMAND_ADD = "/add";
    private static final String COMMAND_VIEW = "/view";
    private static final String COMMAND_DELETE = "/delete";
    private static final String COMMAND_HELP = "/help";
    private static final String COMMAND_ADDME = "/addme";
    private static final String COMMAND_START = "/start";

    private static final String ADMIN_VIEW_USERS = "/viewusers";
    private static final String ADMIN_ADD = "/approveusers";
    private static final String ADMIN_REMOVE_USERS = "/removeuser";

    private static final String ADMIN_VIEW_PENDING = "/viewpending";
    private static final String ADMIN_REMOVE_PENDING = "/removepending";

    private static final String ADMIN_VIEW_REQUEST = "/viewrequest";
    private static final String ADMIN_VIEW_REMIND = "/remind";
    private static final String ADMIN_PUBLISH = "/publish";

    private static final String ADMIN_NEWADMIN = "/newadmin";
    private static final String ADMIN_HELP = "/adminhelp";

    public enum userCommands{
        COMMAND_ADD,
        COMMAND_VIEW,
        COMMAND_DELETE,
        COMMAND_HELP,
        COMMAND_ADDME,
        COMMAND_START,
        COMMAND_INVALID
    }

    public enum adminCommands{
        ADMIN_VIEW_USERS,
        ADMIN_ADD,
        ADMIN_REMOVE_USERS,
        ADMIN_VIEW_PENDING,
        ADMIN_REMOVE_PENDING,
        ADMIN_VIEW_REQUEST,
        ADMIN_REMIND,
        ADMIN_PUBLISH,
        ADMIN_HELP,
        ADMIN_NEWADMIN,
        ADMIN_INVALID
    }

    public static adminCommands parseAdminCommand(String command){
        command = tokenize(command);
        switch (command){
            case ADMIN_VIEW_USERS:
                return adminCommands.ADMIN_VIEW_USERS;
            case ADMIN_ADD:
                return adminCommands.ADMIN_ADD;
            case ADMIN_REMOVE_USERS:
                return adminCommands.ADMIN_REMOVE_USERS;
            case ADMIN_VIEW_PENDING:
                return adminCommands.ADMIN_VIEW_PENDING;
            case ADMIN_REMOVE_PENDING:
                return adminCommands.ADMIN_REMOVE_PENDING;
            case ADMIN_VIEW_REQUEST:
                return adminCommands.ADMIN_VIEW_REQUEST;
            case ADMIN_VIEW_REMIND:
                return adminCommands.ADMIN_REMIND;
            case ADMIN_PUBLISH:
                return adminCommands.ADMIN_PUBLISH;
            case ADMIN_NEWADMIN:
                return adminCommands.ADMIN_NEWADMIN;
            case ADMIN_HELP:
                return adminCommands.ADMIN_HELP;
            default:
                return adminCommands.ADMIN_INVALID;
        }
    }

    public static Parser.userCommands parseUserCommand(String command){
        command = tokenize(command);
        switch (command){
            case COMMAND_ADD:
                return userCommands.COMMAND_ADD;
            case COMMAND_VIEW:
                return userCommands.COMMAND_VIEW;
            case COMMAND_DELETE:
                return userCommands.COMMAND_DELETE;
            case COMMAND_HELP:
                return userCommands.COMMAND_HELP;
            case COMMAND_ADDME:
                return userCommands.COMMAND_ADDME;
            case COMMAND_START:
                return userCommands.COMMAND_START;
            default:
                return userCommands.COMMAND_INVALID;
        }
    }

    public static boolean isAdminCommand(String command){
        command = tokenize(command);

        switch (command) {
            case ADMIN_VIEW_USERS:
                return true;
            case ADMIN_ADD:
                return true;
            case ADMIN_REMOVE_USERS:
                return true;
            case ADMIN_VIEW_PENDING:
                return true;
            case ADMIN_REMOVE_PENDING:
                return true;
            case ADMIN_VIEW_REQUEST:
                return true;
            case ADMIN_VIEW_REMIND:
                return true;
            case ADMIN_PUBLISH:
                return true;
            case ADMIN_NEWADMIN:
                return true;
            case ADMIN_HELP:
                return true;
            default:
                return false;
        }
    }

    private static String tokenize(String command){
        return command.split(" ")[0];
    }
}
