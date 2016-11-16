import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by aloysius on 9/19/16.
 */
public class run {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Scanner sc = new Scanner(System.in);
        //System.out.println("enter code");
        //String token = sc.nextLine();
        String token = JOptionPane.showInputDialog("Bot token pls");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new RegBot(token));
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }
}
