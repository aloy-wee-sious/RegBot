/**
 * Created by aloysius on 10/13/16.
 */
public class Parser {

    public enum userCommands{
        COMMAND_ADD,
        COMMAND_VIEW,
        COMMAND_DELETE,
        COMMAND_HELP,
        COMMAND_ADDME
    }

    public enum adminCommands{
        ADMIN_VIEW_USERS,
        ADMIN_ADD,
        ADMIN_REMOVE_USERS,
        ADMIN_VIEW_PENDING,
        ADMIN_REMOVE_PENDING,
        ADMIN_VIEW_REQUEST,
        ADMIN_VIEW_REMIND,
        ADMIN_PUBLISH,
        ADMIN_HELP
    }

    public static Parser.adminCommands parseAdminCommand(String command){
        //split string to tokens and compare first token
        //return them as commands
        return adminCommands.ADMIN_ADD;
    }

    public static Parser.userCommands parseUserCommand(String command){

        return userCommands.COMMAND_ADD;
    }
}
