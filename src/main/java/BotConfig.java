/**
 * Created by aloysius on 9/19/16.
 */
public class BotConfig {
        public static final String BOT_USERNAME = "RegBot";
        public static String BOT_TOKEN = "{you secret bot token that you got from BotFather}";
        public BotConfig(String input){
            this.BOT_TOKEN = input;
        }
        public String getBotToken() {
            return BOT_TOKEN;
        }
        public String getBotUsername(){
            return BOT_USERNAME;
        }
}
