package com.company.controller;

import com.company.buttons.InlineButtonUtil;
import com.company.container.ComponentContainer;
import com.company.enums.UserStatus;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class MainController {

    public void handleText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        if (text.equals("/start") || text.equals("start")) {
            StringBuilder builder = new StringBuilder();
            builder.append("Welcome <b>");
            builder.append(user.getFirstName());
            builder.append("</b>\n");
            builder.append("<a href=\"https://t.me/UZB_MoneyConverterBot\">This bot</a>");
            builder.append(" is ready to work!");
            sendMessage.setParseMode("HTML");
            sendMessage.setDisableWebPagePreview(true);
            sendMessage.setText(builder.toString());
            sendMessage.setReplyMarkup(InlineButtonUtil.chooseSingleKeyboard());
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);
        } else if (ComponentContainer.userStatusMap.containsKey(user.getId())) {
            String language = text.split(" ")[0];
            String sendMsg = "restart the bot Please";
            if (language.startsWith("English/")) {
                if (language.equals("English/Russian")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "en-ru");
                } else if (language.equals("English/Turkey")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "en-tr");
                }
            }
            if (language.startsWith("Russian/")) {
                if (language.equals("Russian/English")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "ru-en");
                } else if (language.equals("Russian/Turkey")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "ru-tr");
                }
            }
            if (language.startsWith("Turkey/")) {
                if (language.equals("Turkey/English")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "tr-en");
                } else if (language.equals("Turkey/Russian")) {
                    ComponentContainer.userLanguageMap.put(user.getId(), "tr-ru");
                }
            }
            if (ComponentContainer.userLanguageMap.containsKey(user.getId())) {
                ComponentContainer.userStatusMap.remove(user.getId());
                sendMsg = "Send your text: ";
            }
            sendMessage.setText(sendMsg);
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);

        } else if (ComponentContainer.userLanguageMap.containsKey(user.getId())) {
            ComponentContainer.DICTIONARY_CONTROLLER.handleText(user, message);
        } else {
            sendMessage.setText("\uD83D\uDE41");
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);
            sendMessage.setText("Wrong command! Please /start");
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);
        }

    }


    public void handleCallback(User user, Message message, String callback) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(message.getMessageId());
        editMessageText.setChatId(String.valueOf(user.getId()));
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        if (callback.startsWith("/lang")) {
            KeyboardButton button1 = new KeyboardButton();
            KeyboardButton button2 = new KeyboardButton();
            switch (callback) {
                case "/lang/en" -> {
                    ComponentContainer.userStatusMap.put(user.getId(), UserStatus.English);
                    button1 = new KeyboardButton("English/Russian \uD83C\uDDFA\uD83C\uDDF8\uD83C\uDDF7\uD83C\uDDFA");
                    button2 = new KeyboardButton("English/Turkey \uD83C\uDDFA\uD83C\uDDF8\uD83C\uDDF9\uD83C\uDDF7");
                }
                case "/lang/ru" -> {
                    ComponentContainer.userStatusMap.put(user.getId(), UserStatus.Russian);
                    button1 = new KeyboardButton("Russian/English \uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDFA\uD83C\uDDF8");
                    button2 = new KeyboardButton("Russian/Turkey \uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDF9\uD83C\uDDF7");
                }
                case "/lang/tr" -> {
                    ComponentContainer.userStatusMap.put(user.getId(), UserStatus.Turkey);
                    button1 = new KeyboardButton("Turkey/English \uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDF8");
                    button2 = new KeyboardButton("Turkey/Russian \uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF7\uD83C\uDDFA");
                }
            }

            KeyboardRow row1 = new KeyboardRow();
            row1.add(button1);
            row1.add(button2);

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setKeyboard(List.of(row1));

            sendMessage.setText("Choose Language: ");
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);
        }
    }
}



