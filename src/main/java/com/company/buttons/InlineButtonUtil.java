package com.company.buttons;

import com.company.enums.UserStatus;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String text, String callBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);
        return button;
    }

    public static InlineKeyboardButton button(String text, String callBackData, String emoji) {
        String emojiText = EmojiParser.parseToUnicode(emoji + " " + text);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojiText);
        button.setCallbackData(callBackData);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... inlineKeyboardButtons) {
        return new LinkedList<>(Arrays.asList(inlineKeyboardButtons));
    }

    @SafeVarargs
    public static List<List<InlineKeyboardButton>> rowList(List<InlineKeyboardButton>... rows) {
        return new LinkedList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rowList) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

    /**
     * Utils Keyboards
     */

    public static InlineKeyboardMarkup chooseSingleKeyboard() {
        return InlineButtonUtil.keyboard(
                InlineButtonUtil.rowList(
                        InlineButtonUtil.row(
                                InlineButtonUtil.button("English", "/lang/en", "\uD83C\uDDFA\uD83C\uDDF8"),
                                InlineButtonUtil.button("Russian", "/lang/ru", "\uD83C\uDDF7\uD83C\uDDFA"),
                                InlineButtonUtil.button("Turkey", "/lang/tr", "\uD83C\uDDF9\uD83C\uDDF7"))
                ));
    }

    public static InlineKeyboardMarkup languageKeyboard(UserStatus status) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        if (status.equals(UserStatus.English)) {
            markup = InlineButtonUtil.keyboard(
                    InlineButtonUtil.rowList(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("English/Russian", "/en/ru", "\uD83C\uDDFA\uD83C\uDDF8\uD83C\uDDF7\uD83C\uDDFA")),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("English/Turkey", "/en/tr", "\uD83C\uDDFA\uD83C\uDDF8\uD83C\uDDF9\uD83C\uDDF7")

                            )));
        } else if (status.equals(UserStatus.Russian)) {
            markup = InlineButtonUtil.keyboard(
                    InlineButtonUtil.rowList(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Russian/English", "/ru/en", "\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDFA\uD83C\uDDF8")),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Russian/Turkey", "/ru/tr", "\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDF9\uD83C\uDDF7")

                            )));
        } else if (status.equals(UserStatus.Turkey)) {
            markup = InlineButtonUtil.keyboard(
                    InlineButtonUtil.rowList(
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Turkey/English", "/tr/en", "\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDF8")),
                            InlineButtonUtil.row(
                                    InlineButtonUtil.button("Turkey/Russian", "/tr/ru", "\uD83C\uDDF9\uD83C\uDDF7\uD83C\uDDF7\uD83C\uDDFA")

                            )));
        }
        return markup;
    }
}
