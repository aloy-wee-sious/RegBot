import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aloysius on 9/21/16.
 */
public class AdminCommands {

    private static ArrayList<User> admins = new ArrayList<>();
    private static String reggyquote = "HI HI! :)\n" +
            "Please submit your prayer requests here (@ReggyBot)!! Have a great week ahead:)";

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

    public static String newWeek(ArrayList<User> users){
        //TODO new week
        for (User user : users) {
            user.clearRequest();
        }
        return reggyquote;
    }

    public static SendDocument publish(ArrayList<User> users, String chatId) throws FileNotFoundException, DocumentException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String fileName = "ReggyBot/Request/" + dateFormat.format(new Date())+".pdf";
        File file = new File(fileName);

        createPDF(users, fileName);


        SendDocument sendDocument = new SendDocument();
        sendDocument.setNewDocument(file);
        sendDocument.setChatId(chatId);
        sendDocument.setCaption("Hey everyone! Here are the prayer requests! Let's be praying for each other regularly despite the busy period!!");
        return sendDocument;
    }

    private static void createPDF(ArrayList<User> users, String fileName) throws DocumentException, FileNotFoundException {
        Document document = new Document(PageSize.A4, 40, 40 ,40 ,40);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(new float[]{ (PageSize.A4.getWidth()-80)/4, (PageSize.A4.getWidth()-80)*3/4});
        table.setLockedWidth(true);


        table.addCell("Name:");
        table.addCell("Request:");

        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setPadding(5);

        for(User user : users){
            if(user.haveRequest()) {
                cell.setPhrase(new Phrase(user.getName()));
                table.addCell(cell);
                cell.setPhrase(new Phrase(user.printRequest()));
                table.addCell(cell);
            }
        }

        document.add(table);
        document.close();
    }

    public static String help(){
        return "Hello admin! Below are the list of commands available\n" +
                "/viewusers to view all existing users\n" +
                "/newadmin <user list number> to add user to admin\n"+
                "/remove <user list number> to remove existing users\n" +
                "/viewpending to view pending user\n" +
                "/approve <pending list number> to add users from pending users\n" +
                "/removepending <pendinglist number> to remove users from pending\n" +
                "/remind to remind users\n" +
                "/addgroup add selected group to be useed when publish (Use in group chat)\n" +
                "/publish to publish\n" +
                "/newweek to start a new week (All request will be erased! Be sure to publish first!)";
    }

    public static String viewRequest(ArrayList<User> myUsers) {
        return myUsers.toString();
    }

    public static void addDefault(){
        admins.add(new User("Aloysius", null, Long.parseLong("226481140")));
    }
}
