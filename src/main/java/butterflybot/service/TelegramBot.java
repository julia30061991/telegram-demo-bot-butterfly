package butterflybot.service;

import butterflybot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    final BotConfig botConfig;

    public TelegramBot(BotConfig config) {
        this.botConfig = config;
        setBotMenu();
    }

    public String getBotUsername() {
        return botConfig.getBotName();
    }

    public String getBotToken() {
        return botConfig.getBotToken();
    }

    public void onUpdateReceived(Update update) {
        long chatId = 0L;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
            switch (userMessage) {
                case "/start":
                    startCommand(chatId);
                    break;
                case "/registration":
                    regCommand(chatId);
                    break;
                case "/teachers":
                    teacherCommand(chatId);
                    break;
                case "/price":
                    priceCommand(chatId);
                    break;
                case "/timetable":
                    timetableCommand(chatId);
                    break;
                case "/contacts":
                    contactCommand(chatId);
                    break;
                case "/brunches":
                    brunchesCommand(chatId);
                    break;
                case "/help":
                    helpCommand(chatId);
                    break;
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            long chatIdQuery = update.getCallbackQuery().getMessage().getChatId();
            if (data.equals("TELEPHONE_REG") || data.equals("CALL_ME")) {
                sendMessage(chatIdQuery, "Для вызова нажмите +79050433118 ");
            }
        } else {
            String error = "Упс! Что-то пошло не так. Попробуйте позднее";
            sendMessage(chatId, error);
        }
    }

    private void startCommand(long chatId) {
        StringBuilder builder = new StringBuilder();
        builder = builder.append("Привет! \uD83D\uDC4B Я - бот студии \"Butterfly\" - студии танцев на пилоне")
                .append("\n\n Я помогу тебе узнать основную информацию о нас.")
                .append(" Для этого выбери подходящую тебе команду: ")
                .append(" \n\n /registration - чтобы записаться к нам на занятие; ")
                .append(" \n /teachers - чтобы узнать, какие преподаватели есть в студии; ")
                .append(" \n /brunches - чтобы узнать, какие направления ты можешь посетить; ")
                .append(" \n /price - чтобы узнать цены на наши занятия; ")
                .append(" \n /timetable - чтобы узнать расписание студии; ")
                .append(" \n /contacts - чтобы узнать, где мы находимся и как с нами связаться; ")
                .append(" \n /help - если тебе нужна помощь в другом вопросе; ")
                .append(" \n\n ↙ Для быстрого выбора команды можно воспользоваться закрепленным меню слева от формы ввода сообщения")
        ;
        sendMessage(chatId, builder.toString());
    }

    private void regCommand(long chatId) {
        String question = "Как Вы хотите к нам записаться?";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listOfButtons = new ArrayList<>();
        List<InlineKeyboardButton> rowsInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowsInLine2 = new ArrayList<>();

        var buttonYClients = new InlineKeyboardButton();
        var buttonTelephone = new InlineKeyboardButton();

        buttonYClients.setText("Запись через YClients ✍");
        buttonYClients.setCallbackData("YCLIENTS_REG");
        buttonYClients.setUrl("https://n795007.yclients.com/");
        buttonTelephone.setText("Запись по телефону \uD83D\uDCDE");
        buttonTelephone.setCallbackData("TELEPHONE_REG");

        rowsInLine1.add(buttonYClients);
        rowsInLine2.add(buttonTelephone);
        listOfButtons.add(rowsInLine1);
        listOfButtons.add(rowsInLine2);
        markup.setKeyboard(listOfButtons);

        sendMessage(chatId, question, markup);
    }

    private void teacherCommand(long chatId) {
        StringBuilder builder = new StringBuilder();
        builder = builder.append("В студии Butterfly работают 10 лучших тренеров города:")
                .append(" \n\n \uD83D\uDD39 Екатерина Евдокимова: \n - стаж работы - c 2013 г. \n - направления: Pole dance, Exotic pole")
                .append(" \n \uD83D\uDD39 Мария Волкова: \n - стаж работы - с 2016 г. \n - направления: Exotic pole")
                .append(" \n \uD83D\uDD39 Мария Егорышева: \n - стаж работы - с 2019 г. \n - направления: Pole dance, Exotic pole, стрейчинг")
                .append(" \n \uD83D\uDD39 Ольга Цепаева: \n - стаж работы - с 2018 г. \n - направления: Pole dance")
                .append(" \n \uD83D\uDD39 Анастасия Тарасова: \n - стаж работы - с 2019 г. \n - направления: Pole dance, Exotic pole")
                .append(" \n \uD83D\uDD39 Юлия Нейман: \n - стаж работы - с 2018 г. \n - направления: Exotic pole")
                .append(" \n \uD83D\uDD39 Светлана Соколова: \n - стаж работы - с 2019 г. \n - направления: Pole dance")
                .append(" \n \uD83D\uDD39 Анна Золотарева: \n - стаж работы - с 2019 г. \n - направления: Exotic pole")
                .append(" \n \uD83D\uDD39 Татьяна Бровкова: \n - стаж работы - с 2023 г. \n - направления: Exotic pole")
                .append(" \n \uD83D\uDD39 Татьяна Фаустова: \n - стаж работы - с 2022 г. \n - направления: Exotic pole")
                .append("\n\n ✎ Чтобы записаться к нам на занятие, нажмите /registration")
        ;
        sendMessage(chatId, builder.toString());
    }

    private void priceCommand(long chatId) {
        String url = "https://vk.com/butterflypoledance?z=photo-56636266_457253216%2Falbum-56636266_287504072";
        String text = "Актуальный прайс для ознакомления \uD83D\uDCB5 ↑";
        getImage(chatId, url, text);
    }

    private void timetableCommand(long chatId) {
        String url = "https://vk.com/butterflypoledance?z=photo-56636266_457253187%2Falbum-56636266_287525217";
        String text = "Актуальное расписание занятий в лавандовом зале \uD83D\uDCC6 ↑";
        getImage(chatId, url, text);
        String url2 = "https://vk.com/butterflypoledance?z=photo-56636266_457253218%2Falbum-56636266_287525217";
        String text2 = "Актуальное расписание занятий в черно-белом зале \uD83D\uDCC6 ↑";
        getImage(chatId, url2, text2);
    }

    private void contactCommand(long chatId) {
        String answer = "Вы можете связаться с нами одним из следующих способов:" +
                "\n\n \uD83D\uDD38 написать в Telegram \n \uD83D\uDD38 написать в VK \n \uD83D\uDD38 позвонить по телефону" +
                "\n\n \uD83C\uDFE2 Адрес: г. Липецк, просп. Победы, д.29А, 5 этаж, офис 610";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> listMarkup = new ArrayList<>();
        List<InlineKeyboardButton> rowsInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowsInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowsInLine3 = new ArrayList<>();

        var buttonTG = new InlineKeyboardButton();
        var buttonVK = new InlineKeyboardButton();
        var buttonCall = new InlineKeyboardButton();

        buttonTG.setText("Написать в TG ✎");
        buttonTG.setCallbackData("WRITE_TG");
        buttonTG.setUrl("t.me/Irina_bytterfly");
        buttonVK.setText("Написать в VK ✍");
        buttonVK.setCallbackData("WRITE_VK");
        buttonVK.setUrl("https://vk.com/im?media=&sel=-56636266");
        buttonCall.setText("Позвонить \uD83D\uDCDE");
        buttonCall.setCallbackData("CALL_ME");

        rowsInLine1.add(buttonTG);
        rowsInLine2.add(buttonVK);
        rowsInLine3.add(buttonCall);

        listMarkup.add(rowsInLine1);
        listMarkup.add(rowsInLine2);
        listMarkup.add(rowsInLine3);
        markup.setKeyboard(listMarkup);

        sendMessage(chatId, answer, markup);
    }

    public void brunchesCommand(long chatId) {
        String answer = "Основные направления студии: \n\n \uD83D\uDD38 Pole Dance - шестовая акробатика: изучение " +
                "различных трюковых элементов, их комбинаций и переходов между ними" +
                "\n\n \uD83D\uDD38 Exotic Pole Dance - изучение танцевальных связок с пилоном и в партере в специализированной" +
                " обуви и под музыку" + "\n\n \uD83D\uDD38 Стрейчинг - активная и пассивная растяжка, работа над " +
                "гибкостью тела \n\n ✎ Чтобы записаться к нам на занятие, нажмите /registration";
        sendMessage(chatId, answer);
    }

    public void helpCommand(long chatId) {
        String answer = "Если Вы не нашли необходимую информацию, то воспользуйтесь командой /contacts и " +
                "свяжитесь с нами одним из указанных способов. Мы ответим на любой Ваш вопрос!";
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String sendMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sendMessage);
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            ex.getMessage();
        }
    }

    public void sendMessage(long chatId, String sendMessage, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sendMessage);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            ex.getMessage();
        }
    }

    private void getImage(long chatId, String imageUrl, String text) {
        try {
            URL url = new URL(imageUrl);
            InputFile photo = new InputFile(String.valueOf(url));
            SendPhoto sPhoto = new SendPhoto();
            sPhoto.setPhoto(photo);
            sPhoto.setChatId(String.valueOf(chatId));
            sPhoto.setCaption(text);
            execute(sPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBotMenu() {
        List<BotCommand> listMenu = new ArrayList<>();
        listMenu.add(new BotCommand("/start", "Запуск бота"));
        listMenu.add(new BotCommand("/registration", "Запись на занятия"));
        listMenu.add(new BotCommand("/teachers", "Преподаватели"));
        listMenu.add(new BotCommand("/price", "Актуальные цены"));
        listMenu.add(new BotCommand("/timetable", "Расписание"));
        listMenu.add(new BotCommand("/contacts", "Контакты"));
        listMenu.add(new BotCommand("/brunches", "Направления"));
        listMenu.add(new BotCommand("/help", "Помощь"));
        try {
            this.execute(new SetMyCommands(listMenu, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}