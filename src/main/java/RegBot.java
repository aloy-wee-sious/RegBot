import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.io.*;
import java.util.ArrayList;
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
        } catch(Exception e) {
            System.out.println(e.getMessage());
            AdminCommands.addDefault();
            users.add(new User("Aloysius", null, Long.parseLong("226481140")));
        }
        users.add(new User("fake", "guy", (long)0000000000));
        approvalList.add(new User("waiting", "guy", (long)111111111));
    }
    private ArrayList<User> approvalList = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private long groupChatID;

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            System.out.println(update);
            Message message = update.getMessage();
            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                if (message.isUserMessage()) {
                    System.out.println("user pm with text " + message.getText());
                    String parameters = formatText(message.getText());
                    String reply = "";
                    if(Parser.isAdminCommand(parameters) && AdminCommands.isAdmin(message.getFrom().getId())){
                        Parser.adminCommands adminCommand = Parser.parseAdminCommand(parameters);
                        parameters = removeCommand(parameters);
                        try {
                            reply = processAdminCommands(adminCommand, parameters);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception ex) {
                            reply = ex.getMessage();
                        }
                    }else{
                        Parser.userCommands userCommand = Parser.parseUserCommand(parameters);
                        parameters = removeCommand(parameters);
                        try {
                            reply = processUserCommands(userCommand, new User( message.getFrom().getFirstName(), message.getFrom().getLastName(), message.getFrom().getId()), parameters);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception ex) {
                            reply = ex.getMessage();
                        }
                    }
                    sendReply(message, sendMessage, reply);
                } else if (message.isGroupMessage() && Parser.isAdminCommand(formatText(message.getText())) && Parser.parseAdminCommand(formatText(message.getText())).equals(Parser.adminCommands.ADMIN_ADD_GROUP) && AdminCommands.isAdmin(message.getFrom().getId())) {
                    groupChatID = message.getChatId();
                    try {
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sendReply(message, sendMessage, "Added group");
                }

            }

        }
    }

    private String removeCommand(String text) {
        return text.substring(text.indexOf(" ")+1,text.length());
    }

    private String processUserCommands(Parser.userCommands command, User user, String text) throws IOException, UserNotFoundException, InvalidParameterException, AlreadyExistException {
        String reply;
        switch(command) {
            case COMMAND_ADD:
                this.users = UserCommands.add(users, user.getUserId(),text);
                save();
                reply = "Request added";
                break;
            case COMMAND_DELETE:
                //System.out.println("delete number " + text);
                this.users = UserCommands.delete(users, user.getUserId(),Integer.parseInt(text));
                save();;
                reply = "Request deleted";
                break;
            case COMMAND_VIEW:
                reply = UserCommands.view(users, user.getUserId());
                break;
            case COMMAND_HELP:
                reply = UserCommands.help();
                break;
            case COMMAND_ADDME:
                approvalList = UserCommands.addme(users, approvalList, user);
                save();;
                reply = "Added to waiting list for admin approval";
                break;
            case COMMAND_START:
                reply = UserCommands.start();
                break;
            default:
                reply = "Invalid command";
                break;
        }
        return reply;
    }

    private String processAdminCommands(Parser.adminCommands command, String text) throws IOException, InvalidParameterException {
        String reply = "Invalid command";
        switch(command) {
            case ADMIN_ADD:
                System.out.println("admin add");
                users = AdminCommands.addUser(users, approvalList, Integer.parseInt(text));
                approvalList = AdminCommands.removePending(approvalList, Integer.parseInt(text));
                save();;
                reply = "User added";
                break;
            case ADMIN_PUBLISH:
                //TODO
                reply = "admin publish";
                break;
            case ADMIN_REMOVE_PENDING:
                approvalList = AdminCommands.removePending(approvalList, Integer.parseInt(text));
                save();;
                reply = "User removed";
                break;
            case ADMIN_REMOVE_USERS:
                AdminCommands.removeUser(users, Integer.parseInt(text));
                save();;
                reply = "User removed";
                break;
            case ADMIN_REMIND:
                reply = "need remind\n"+ AdminCommands.remind(users);
                break;
            case ADMIN_VIEW_REQUEST:
                //TODO
                reply = AdminCommands.viewRequest(users);
                System.out.println("admin view request");
                break;
            case ADMIN_VIEW_USERS:
                reply = AdminCommands.viewUsers(users);
                break;
            case ADMIN_VIEW_PENDING:
                reply = AdminCommands.viewPending(approvalList);
                break;
            case ADMIN_HELP:
                reply = AdminCommands.help();
                break;
            case ADMIN_NEWADMIN:
                AdminCommands.addAdmin(users, Integer.parseInt(text));
                save();;
                reply = AdminCommands.getAdminsName().toString();
                break;
            case ADMIN_INVALID:
                break;
            default:
                break;
        }
        return reply;
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

    public void save() throws IOException {

        File directory = new File("ReggyBot");

        if (!directory.exists()) {
            directory.mkdir();
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("ReggyBot/users.json"), users);
        mapper.writeValue(new File("ReggyBot/approvalList.json"), approvalList);
        mapper.writeValue(new File("ReggyBot/admins.json"), AdminCommands.getAdmins());
        mapper.writeValue(new File("ReggyBot/groupID.json"), groupChatID);
    }

    private void load() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        users = objectMapper.readValue(new File("ReggyBot/users.json"), new TypeReference<List<User>>(){});
        AdminCommands.setAdmins(objectMapper.readValue(new File("ReggyBot/admins.json"), new TypeReference<List<User>>(){}));
        approvalList = objectMapper.readValue(new File("ReggyBot/approvalList.json"), new TypeReference<List<User>>(){});
        groupChatID = objectMapper.readValue(new File("ReggyBot/groupID.json"), Long.TYPE);
    }

    @Override
    public String getBotToken() {
        return this.config.getBotToken();
    }
}