import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aloysius on 9/19/16.
 */
public class RegBot extends TelegramLongPollingBot {

    private BotConfig config;
    public RegBot(String input)  {
        this.config = new BotConfig(input);
        try {
            load();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private ArrayList<User> seekApproval = new ArrayList<>(Arrays.asList(new User("wait", "longlong", (long)1111111111)));
    private ArrayList<User> myUsers = new ArrayList<User>(Arrays.asList(new User("Aloysius", "Wang", (long)226481140), new User("fakeguy", null, (long)000000000)));


    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println(update);
            Message message = update.getMessage();
            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                if (message.isUserMessage()) {
                    System.out.println("user pm with text " + message.getText());
                    String text = formatText(message.getText());
                    String reply = "";
                    if(Parser.isAdminCommand(text) && AdminCommands.isAdmin(message.getFrom().getId())){
                        Parser.adminCommands adminCommand = Parser.parseAdminCommand(text);
                        text = removeCommand(text);
                        reply = processAdminCommands(adminCommand, text);
                        System.out.println(adminCommand);
                    }else{
                        Parser.userCommands userCommand = Parser.parseUserCommand(text);
                        text = removeCommand(text);
                        reply = processUserCommands(userCommand, new User( message.getFrom().getFirstName(), message.getFrom().getLastName(), message.getFrom().getId()), text);
                        System.out.println(userCommand);
                        try {
                            //FIXME to be use after write commands only
                            save(myUsers,seekApproval);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    boolean isAdmin = AdminCommands.isAdmin(message.getFrom().getId());
                    System.out.println("is admin command " + isAdminCommand(formatText(message.getText())));
                    System.out.println("is admin " + isAdmin+"");
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

    private String processUserCommands(Parser.userCommands command, User user, String text) {
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
                reply = "ok";
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

    private String processAdminCommands(Parser.adminCommands command, String text) {
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
                reply = "admin publish";
                break;
            case ADMIN_REMOVE_PENDING:
                System.out.println("admin remove pending");
                reply = "remove pending";
                break;
            case ADMIN_REMOVE_USERS:
                System.out.println("admin remove user");
                AdminCommands.removeUser(myUsers, Integer.parseInt(text));
                reply = "remove user";
                break;
            case ADMIN_VIEW_REMIND:
                System.out.println("admin remind user");
                reply = "admin view remind";
                break;
            case ADMIN_VIEW_REQUEST:
                System.out.println("admin view request");
                reply = "request";
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
                reply = "admin help";
                break;
            case ADMIN_NEWADMIN:
                System.out.println("add new admin");
                AdminCommands.addAdmin(myUsers, Integer.parseInt(text));
                reply = AdminCommands.getAdminsName().toString();
                break;
            default:
                reply = command.toString();
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

    public void save(ArrayList<User> myUsers, ArrayList<User> seekApproval) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("myUsers.json"), myUsers);
        mapper.writeValue(new File("seekApproval.json"), seekApproval);
        mapper.writeValue(new File("admins.json"), AdminCommands.getAdmins());
    }

    private void load() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        myUsers = objectMapper.readValue(new File("myUsers.json"), new TypeReference<List<User>>(){});
        AdminCommands.setAdmins(objectMapper.readValue(new File("admins.json"), new TypeReference<List<User>>(){}));
        seekApproval = objectMapper.readValue(new File("seekApproval.json"), new TypeReference<List<User>>(){});
    }

    @Override
    public String getBotToken() {
        return this.config.getBotToken();
    }
}