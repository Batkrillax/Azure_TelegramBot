package fis11_telegram_bot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Solution extends BotCommand {

    public Solution() {
        super("Solution", "calculate");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        String chatId = chat.getId().toString();

        if (arguments != null && arguments.length == 3) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chatId);
            answer.setParseMode(ParseMode.MARKDOWN);
            try {
                StringBuilder messageTextBuilder = new StringBuilder("`x = ");

                double x = Double.parseDouble(arguments[0]);
                messageTextBuilder.append(x).append("`\n");

                double a = Double.parseDouble(arguments[1]);
                messageTextBuilder.append("`a = ").append(a).append("`\n");

                double b = Double.parseDouble(arguments[2]);
                messageTextBuilder.append("`b = ").append(b).append("`\n\n");

                double result = Solution(x, a, b);

                messageTextBuilder.append("*y = ").append(result).append("*");
                answer.setText(messageTextBuilder.toString());

            } catch (NumberFormatException e) {
                answer.setText("*Аргументы введены неправильно.*");
            }

            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
            }

        } else {
            SendPhoto answer = MyTelegramBot.getDefaultMessage(chatId);
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
            }
        }
    }

    private double Solution(double x, double a, double b) {

        double result;

        if (x >= 4) {
            result = 10 * (x + a * a) / (b + a);
        } else {
            result = 5 * (x + a * a + b);
        }

        return result;
    }
}
