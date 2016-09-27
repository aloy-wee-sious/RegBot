import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by aloysius on 9/19/16.
 */
public class RegBot extends TelegramLongPollingBot {

    private BotConfig config;
    public RegBot(String input) {
        this.config = new BotConfig(input);
    }

    private final String COMMAND_ADD = "/add";
    private final String COMMAND_VIEW = "/view";
    private final String COMMAND_DELETE = "/delete";
    private final String COMMAND_HELP = "/help";
    private final String COMMAND_ADDME = "/addme";

    private ArrayList<User> seekApproval = new ArrayList<User>();
    private ArrayList<User> myUsers = new ArrayList<User>(Arrays.asList(new User("Aloysius", "Wang", (long)226481140), new User("fakeguy", null, (long)000000000)));

    private final String ADMIN_VIEW_USERS = "/adminviewusers";
    private final String ADMIN_ADD = "/adminadd";
    private final String ADMIN_REMOVE_USERS = "/adminremoveuser";

    private final String ADMIN_VIEW_PENDING = "/adminviewpending";
    private final String ADMIN_REMOVE_PENDING = "/adminremovepending";

    private final String ADMIN_VIEW_REQUEST = "/adminviewrequest";
    private final String ADMIN_VIEW_REMIND = "/adminremind";
    private final String ADMIN_PUBLISH = "/adminpublish";

    private final String ADMIN_HELP = "/adminhelp";



    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println(update);
            Message message = update.getMessage();
            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                if (message.isUserMessage()) {
                    System.out.println("user pm with text " + message.getText());
                    String text = formatText(message.getText());
                    String command = getCommand(text);
                    text = removeCommand(text);
                    String reply = "";
                    boolean isAdmin = AdminCommands.isAdmin(message.getFrom().getId());
                    System.out.println("is admin command " + isAdminCommand(formatText(message.getText())));
                    System.out.println("is admin " + isAdmin+"");
                    System.out.println(command);
                    if(isAdminCommand(text) && isAdmin){
                        reply = processAdminCommands(command);
                    }else{
                        reply = processUserCommands(command, message.getFrom().getId(), text);
                    }
                    sendReply(message, sendMessage, reply);
                } else if (message.isGroupMessage() && message.getText().contains("@ReggyBot")) {
                    String reply = formatText(message.getText());
                    System.out.println("someone one mentioned me with text " + message.getText());
                    sendReply(message, sendMessage, reply);
                }

            }

        }
    }

    private String removeCommand(String text) {
        return text.substring(text.indexOf(" ")+1,text.length());
    }

    private String processUserCommands(String command, long id, String text) {
        String reply;
        switch(command) {
            case COMMAND_ADD:
                System.out.println("add text " + text);
                this.myUsers = UserCommands.add(myUsers,id,text);
                reply = "added";
                break;
            case COMMAND_DELETE:
                reply = "delete";
                System.out.println("delete number " + text);
                this.myUsers= UserCommands.delete(myUsers,id,Integer.parseInt(text));
                break;
            case COMMAND_VIEW:
                reply = UserCommands.view(myUsers,id);
                System.out.println(UserCommands.view(myUsers,id));
                System.out.println("view");
                break;
            case COMMAND_HELP:
                reply = COMMAND_HELP;
                System.out.println("help");
                break;
            case COMMAND_ADDME:
                reply = COMMAND_ADDME;
                System.out.println("addme");
                break;
            default:
                reply = "invalid";
                break;
        }
        return reply;
    }

    private String processAdminCommands(String command) {
        System.out.println("command is " + command);
        String reply;
        switch(command) {
            case ADMIN_ADD:
                System.out.println("admin add");
                reply = ADMIN_ADD;
                break;
            case ADMIN_PUBLISH:
                System.out.println("admin publish");
                reply = ADMIN_PUBLISH;
                break;
            case ADMIN_REMOVE_PENDING:
                System.out.println("admin remove pending");
                reply = ADMIN_REMOVE_PENDING;
                break;
            case ADMIN_REMOVE_USERS:
                System.out.println("admin remove user");
                reply = ADMIN_REMOVE_USERS;
                break;
            case ADMIN_VIEW_REMIND:
                System.out.println("admin remind user");
                reply = ADMIN_VIEW_REMIND;
                break;
            case ADMIN_VIEW_REQUEST:
                System.out.println("admin view request");
                reply = ADMIN_VIEW_REQUEST;
                break;
            case ADMIN_VIEW_USERS:
                System.out.println("admin view users");
                reply = AdminCommands.viewUsers(myUsers);
                break;
            case ADMIN_VIEW_PENDING:
                System.out.println("admin view pending");
                reply = ADMIN_VIEW_PENDING;
                break;
            case ADMIN_HELP:
                System.out.println("admin help");
                reply = ADMIN_HELP;
                break;
            default:
                reply = "invalid";
                break;
        }
        return reply;
    }

    private boolean isAdminCommand(String text) {
        if(text.length()>"/admin".length()) {
            return text.substring(0, "/admin".length()).equals("/admin");
        }else{
            return false;
        }
    }

    private String getCommand(String reply) {
        if(reply.contains(COMMAND_ADDME)){
            return COMMAND_ADDME;
        }else if(reply.contains(ADMIN_ADD)){
            return ADMIN_ADD;
        }else if(reply.contains(ADMIN_PUBLISH)){
            return ADMIN_PUBLISH;
        }else if(reply.contains(ADMIN_REMOVE_PENDING)){
            return ADMIN_REMOVE_PENDING;
        }else if(reply.contains(ADMIN_REMOVE_USERS)){
            return ADMIN_REMOVE_USERS;
        }else if(reply.contains(ADMIN_VIEW_PENDING)){
            return ADMIN_VIEW_PENDING;
        }else if(reply.contains(ADMIN_VIEW_REMIND)){
            return ADMIN_VIEW_REMIND;
        }else if(reply.contains(ADMIN_VIEW_REQUEST)){
            return ADMIN_VIEW_REQUEST;
        }else if(reply.contains(ADMIN_VIEW_USERS)){
            return ADMIN_VIEW_USERS;
        }else if(reply.contains(COMMAND_ADD)){
            return COMMAND_ADD;
        }else if(reply.contains(COMMAND_DELETE)){
            return COMMAND_DELETE;
        }else if(reply.contains(COMMAND_HELP)) {
            return COMMAND_HELP;
        }else if(reply.contains(COMMAND_VIEW)) {
            return COMMAND_VIEW;
        }else if(reply.contains(ADMIN_HELP)){
            return ADMIN_HELP;
        }else{
            return "invalid";
        }
    }

    private String formatText(String input){
        String result = input.trim().replaceAll(" +", " ").replace("@ReggyBot ", "");

        return result;
    }
    private void sendReply(Message message, SendMessage sendMessage, String reply) {
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(reply);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return this.config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return this.config.getBotToken();
    }
}