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
        //String input = sc.nextLine();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new RegBot(JOptionPane.showInputDialog("Bot token pls")));
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }
}
