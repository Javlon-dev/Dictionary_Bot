package com.company;

import com.company.container.ComponentContainer;
import okhttp3.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            DictionaryBot dictionaryBot = new DictionaryBot();
            ComponentContainer.DICTIONARY_BOT = dictionaryBot;
            telegramBotsApi.registerBot(dictionaryBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


}
