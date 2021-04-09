package fis11_telegram_bot;

import java.io.File;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class MyTelegramBot extends TelegramLongPollingCommandBot {

    public static SendPhoto getDefaultMessage(String chatId) {
        return SendPhoto
                .builder()
                .chatId(chatId)
                .photo(new InputFile(new File("Вариант.png").getAbsoluteFile()))
                .parseMode(ParseMode.MARKDOWN)
                .caption("Чтобы рассчитать математическое значение по заданному алгоритму введите команду '/Solution', "
                        + "указав после него три аргумента через пробел\n\n"
                        + "Например, если 'x' = 1, 'a' = 2, 'b' = 3: '/Solution 1 2 3'")
                .build();
    }

    public MyTelegramBot() {

        register(new Solution());

        registerDefaultAction((absSender, message) -> {
            SendMessage commandUnknownMessage = SendMessage
                    .builder()
                    .chatId(message.getChatId().toString())
                    .text("Команда " + message.getText() + " неизвестна этому боту.")
                    .build();
            try {
                absSender.execute(commandUnknownMessage);
                absSender.execute(getDefaultMessage(message.getChatId().toString()));
            } catch (TelegramApiException e) {
            }
        });
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();

            try {
                execute(getDefaultMessage(message.getChatId().toString()));
            } catch (TelegramApiException e) {
            }
        }
    }

    //Получаем никнейм бота
    @Override
    public String getBotUsername() {
        return "FIS11bot";
    }

    //Получаем токен бота
    @Override
    public String getBotToken() {
        return "secret";
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new MyTelegramBot());
        } catch (TelegramApiException e) {
        }
    }
}
