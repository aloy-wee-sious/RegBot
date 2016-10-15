import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by aloysius on 9/19/16.
 */
public class run {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("enter code");
        String input = sc.nextLine();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new RegBot(input));
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }
}
