package com.company.controller;

import com.company.buttons.InlineButtonUtil;
import com.company.container.ComponentContainer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;

public class DictionaryController {

    private static final String token = "dict.1.1.20220225T120829Z.2926f516e25b4e67.3cd86f04eb79f46810d22c0a477f8480e7983141";

    public void handleText(User user, Message message) {
        try {
            String text = message.getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(user.getId()));
            sendMessage.setText(printDictionary(ComponentContainer.userLanguageMap.get(user.getId()), text));
            sendMessage.setReplyMarkup(InlineButtonUtil.chooseSingleKeyboard());
            ComponentContainer.DICTIONARY_BOT.send(sendMessage);
            ComponentContainer.userLanguageMap.remove(user.getId());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private String printDictionary(String language, String text) {
        JSONObject jsonObject = new JSONObject(OKHTTPRequest(language, text));
        JSONArray jsonArray = jsonObject.getJSONArray("def");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            builder.append("\n\n\nText: " + jsonObject1.get("text"));
            builder.append("\nIt is: " + jsonObject1.get("pos"));
            JSONArray jsonArray1;
            if (!jsonObject1.getJSONArray("tr").isEmpty()) {
                jsonArray1 = jsonObject1.getJSONArray("tr");
                for (int j = 0; j < jsonArray1.toList().size(); j++) {
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                    builder.append("\n\nTranslate: " + jsonObject2.get("text"));
                    builder.append("\nIt is: " + jsonObject2.get("pos"));
                   /* JSONArray jsonArray2;
                    JSONObject jsonObject3;
                    if (jsonObject1.getJSONArray("syn").toList().isEmpty()) {
                        break;
                    } else {
                        jsonArray2 = jsonObject1.getJSONArray("syn");
                        for (int k = 0; k < jsonArray2.toList().size(); k++) {
                            jsonObject3 = jsonArray2.getJSONObject(k);
                            builder.append("\n\nSynonym: " + jsonObject3.get("text"));
                            builder.append("\nIt is: " + jsonObject3.get("pos"));
                        }
                    }
                    if (jsonObject1.getJSONArray("mean").toList().isEmpty()) {
                        break;
                    } else {
                        jsonArray2 = jsonObject1.getJSONArray("mean");
                        for (int n = 0; n < jsonArray2.toList().size(); n++) {
                            jsonObject3 = jsonArray2.getJSONObject(n);
                            builder.append("\nMean: " + jsonObject3.get("text"));
                        }
                    }*/
                }
            }
        }

        return builder.toString();
    }

    private String OKHTTPRequest(String language, String text) {

        StringBuilder builder = new StringBuilder();
        builder.append("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=");
        builder.append(token);
        builder.append("&lang=");
        builder.append(language);
        builder.append("&text=");
        builder.append(text);

        Request request = new Request.Builder()
                .url(builder.toString())
                .build();

        try {
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
