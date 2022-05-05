package com.company.container;


import com.company.DictionaryBot;
import com.company.controller.DictionaryController;
import com.company.enums.UserStatus;

import java.util.HashMap;
import java.util.Map;


public abstract class ComponentContainer {

    public static DictionaryBot DICTIONARY_BOT;
    public static DictionaryController DICTIONARY_CONTROLLER = new DictionaryController();
    public static Map<Long, UserStatus> userStatusMap = new HashMap<>();
    public static Map<Long, String> userLanguageMap = new HashMap<>();



}
