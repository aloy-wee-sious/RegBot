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
    private final String COMMAND_START = "/start";

    private ArrayList<User> seekApproval = new ArrayList<>(Arrays.asList(new User("wait", "longlong", (long)1111111111)));
    private ArrayList<User> myUsers = new ArrayList<User>(Arrays.asList(new User("Aloysius", "Wang", (long)226481140), new User("fakeguy", null, (long)000000000)));

    private final String ADMIN_VIEW_USERS = "/adminviewusers";
    private final String ADMIN_ADD = "/adminadd";
    private final String ADMIN_REMOVE_USERS = "/adminremoveuser";

    private final String ADMIN_VIEW_PENDING = "/adminviewpending";
    private final String ADMIN_REMOVE_PENDING = "/adminremovepending";

    private final String ADMIN_VIEW_REQUEST = "/adminviewrequest";
    private final String ADMIN_VIEW_REMIND = "/adminremind";
    private final String ADMIN_PUBLISH = "/adminpublish";

    private final String ADMIN_NEWADMIN = "/adminnew";
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
                    if(isAdminCommand(message.getText()) && isAdmin){
                        reply = processAdminCommands(command, text);
                    }else{
                        reply = processUserCommands(command, new User( message.getFrom().getFirstName(), message.getFrom().getLastName(), message.getFrom().getId()), text);
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

    private String processUserCommands(String command, User user, String text) {
        String reply;
        switch(command) {
            case COMMAND_ADD:
                System.out.println("add text " + text);
                this.myUsers = UserCommands.add(myUsers, user.getUserId(),text);
                reply = "added";
                break;
            case COMMAND_DELETE:
                reply = "delete";
                System.out.println("delete number " + text);
                this.myUsers= UserCommands.delete(myUsers, user.getUserId(),Integer.parseInt(text));
                break;
            case COMMAND_VIEW:
                reply = UserCommands.view(myUsers, user.getUserId());
                System.out.println(UserCommands.view(myUsers, user.getUserId()));
                System.out.println("view");
                break;
            case COMMAND_HELP:
                reply = UserCommands.help();
                System.out.println("help");
                break;
            case COMMAND_ADDME:
                int before = seekApproval.size();
                seekApproval = UserCommands.addme(myUsers, seekApproval, user);
                if(before == seekApproval.size()){
                    System.out.println("no change");
                }
                reply = COMMAND_ADDME;
                System.out.println("addme");
                break;
            case COMMAND_START:
                reply = UserCommands.start();
                break;
            default:
                reply = "invalid";
                break;
        }
        return reply;
    }

    private String processAdminCommands(String command, String text) {
        System.out.println("command is " + command);
        String reply = "";
        switch(command) {
            case ADMIN_ADD:
                System.out.println("admin add");
                myUsers = AdminCommands.addUser(myUsers, seekApproval, Integer.parseInt(text));
                seekApproval = AdminCommands.removePending(seekApproval , Integer.parseInt(text));
                reply = "ok";
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
                AdminCommands.removeUser(myUsers, Integer.parseInt(text));
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
                reply = AdminCommands.viewPending(seekApproval);
                if(seekApproval.isEmpty()){
                    reply = "No users";
                }
                break;
            case ADMIN_HELP:
                System.out.println("admin help");
                reply = ADMIN_HELP;
                break;
            case ADMIN_NEWADMIN:
                System.out.println("add new admin");
                AdminCommands.addAdmin(myUsers, Integer.parseInt(text));
                reply = AdminCommands.getAdmins().toString();
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

    private String getCommand(String text) {

        if((text.length() > "/admin".length() && text.substring(0,"/admin".length()).equals("/admin"))){
            if(text.length() >= ADMIN_ADD.length() && text.substring(0, ADMIN_ADD.length()).equals(ADMIN_ADD)) {
                return ADMIN_ADD;
            }else if(text.length() >= ADMIN_PUBLISH.length() && text.substring(0, ADMIN_PUBLISH.length()).equals(ADMIN_PUBLISH)){
                return ADMIN_PUBLISH;
            }else if(text.length() >= ADMIN_REMOVE_PENDING.length() && text.substring(0, ADMIN_REMOVE_PENDING.length()).equals(ADMIN_REMOVE_PENDING)){
                return ADMIN_REMOVE_PENDING;
            }else if(text.length() >= ADMIN_REMOVE_USERS.length() && text.substring(0, ADMIN_REMOVE_USERS.length()).equals(ADMIN_REMOVE_USERS)){
                return ADMIN_REMOVE_USERS;
            }else if(text.length() >= ADMIN_VIEW_PENDING.length() && text.substring(0, ADMIN_VIEW_PENDING.length()).equals(ADMIN_VIEW_PENDING)){
                return ADMIN_VIEW_PENDING;
            }else if(text.length() >= ADMIN_VIEW_REMIND.length() && text.substring(0, ADMIN_VIEW_REMIND.length()).equals(ADMIN_VIEW_REMIND)){
                return ADMIN_VIEW_REMIND;
            }else if(text.length() >= ADMIN_VIEW_REQUEST.length() && text.substring(0, ADMIN_VIEW_REQUEST.length()).equals(ADMIN_VIEW_REQUEST)){
                return ADMIN_VIEW_REQUEST;
            }else if(text.length() >= ADMIN_VIEW_USERS.length() && text.substring(0, ADMIN_VIEW_USERS.length()).equals(ADMIN_VIEW_USERS)){
                return ADMIN_VIEW_USERS;
            }else if(text.length() >= ADMIN_HELP.length() && text.substring(0, ADMIN_HELP.length()).equals(ADMIN_HELP)){
                return ADMIN_HELP;
            }else if(text.length() >= ADMIN_NEWADMIN.length() && text.substring(0, ADMIN_NEWADMIN.length()).equals(ADMIN_NEWADMIN)) {
                return ADMIN_NEWADMIN;
            }else{
                return "invalid";
            }
        }else{
            if(text.length() >= COMMAND_ADDME.length() && text.substring(0, COMMAND_ADDME.length()).equals(COMMAND_ADDME)){
                return COMMAND_ADDME;
            }else if(text.length() >= COMMAND_ADD.length() && text.substring(0, COMMAND_ADD.length()).equals(COMMAND_ADD)){
                return COMMAND_ADD;
            }else if(text.length() >= COMMAND_DELETE.length() && text.substring(0, COMMAND_DELETE.length()).equals(COMMAND_DELETE)){
                return COMMAND_DELETE;
            }else if(text.length() >= COMMAND_HELP.length() && text.substring(0, COMMAND_HELP.length()).equals(COMMAND_HELP)){
                return COMMAND_HELP;
            }else if(text.length() >= COMMAND_VIEW.length() && text.substring(0, COMMAND_VIEW.length()).equals(COMMAND_VIEW)){
                return COMMAND_VIEW;
            }else if(text.length() >= COMMAND_START.length() && text.substring(0, COMMAND_START.length()).equals(COMMAND_START)){
                return COMMAND_START;
            }else{
                return "invalid";
            }
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