package com.tzf.libo.data;

import com.tzf.libo.model.InputItem;
import com.tzf.libo.util.DBConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private static Map<String, Integer> sendTypes = new HashMap<>();

    private static Map<String, Integer> moneyTypes = new HashMap<>();

    private static Map<String, Integer> reasons = new HashMap<>();

    private static Map<String, Integer> subjects = new HashMap<>();

    public static String getBaseKey(int value, int type) {
        String result = null;
        Map<String, Integer> map = null;
        switch (type) {
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                map = getMoneyTypes();
                break;
            case DBConstants.COMMON_ITEM_SENDTYPE:
                map = getSendTypes();
                break;
            case DBConstants.COMMON_ITEM_REASON:
                map = getReasons();
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                map = getSubjects();
                break;
        }
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Integer> m : map.entrySet()) {
                if (m.getValue() == value) {
                    result = m.getKey();
                    break;
                }
            }
        }
        return result;
    }

    public static void setBaseMap(List<InputItem> list, int type) {
        Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.clear();

        if (list != null && list.size() > 0) {
            for (InputItem inputItem : list) {
                tempMap.put(inputItem.getName(), inputItem.getId());
            }

            switch (type) {
                case DBConstants.COMMON_ITEM_MONEYTYPE:
                    setMoneyTypes(tempMap);
                    break;
                case DBConstants.COMMON_ITEM_SENDTYPE:
                    setSendTypes(tempMap);
                    break;
                case DBConstants.COMMON_ITEM_REASON:
                    setReasons(tempMap);
                    break;
                case DBConstants.COMMON_ITEM_SUBJECT:
                    setSubjects(tempMap);
                    break;
            }
        }
    }

    public static Map<String, Integer> getBaseMap(int type) {
        Map<String, Integer> map = null;
        switch (type) {
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                map = getMoneyTypes();
                break;
            case DBConstants.COMMON_ITEM_SENDTYPE:
                map = getSendTypes();
                break;
            case DBConstants.COMMON_ITEM_REASON:
                map = getReasons();
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                map = getSubjects();
                break;
        }
        return map;
    }

    private static Map<String, Integer> getMoneyTypes() {
        return moneyTypes;
    }

    private static void setMoneyTypes(Map<String, Integer> moneyTypes) {
        DataManager.moneyTypes = moneyTypes;
    }

    private static Map<String, Integer> getReasons() {
        return reasons;
    }

    private static void setReasons(Map<String, Integer> reasons) {
        DataManager.reasons = reasons;
    }

    private static Map<String, Integer> getSubjects() {
        return subjects;
    }

    private static void setSubjects(Map<String, Integer> subjects) {
        DataManager.subjects = subjects;
    }

    private static Map<String, Integer> getSendTypes() {
        return sendTypes;
    }

    private static void setSendTypes(Map<String, Integer> sendTypes) {
        DataManager.sendTypes = sendTypes;
    }

}
