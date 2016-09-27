import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import java.util.Scanner;

/**
 * Created by aloysius on 9/19/16.
 */
public class run {

    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        //System.out.println("enter code");
        //String input = sc.nextLine();
        String code = System.getenv().get("CODE");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new RegBot(code));
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }
}
