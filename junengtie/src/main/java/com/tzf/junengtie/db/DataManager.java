package com.tzf.junengtie.db;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author tangzhifei on 15/11/6.
 */
public class DataManager {

    public static List<Message> getMessageByStatus(int status) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("status", status).findAll();
        realmResults.sort("updateTime", RealmResults.SORT_ORDER_DESCENDING);
        List<Message> messageList = new ArrayList<>();
        if (realmResults.size() > 0) {
            for (Message message : realmResults) {
                Message msg = new Message();
                msg.setStatus(message.getStatus());
                msg.setContent(message.getContent());
                msg.setUpdateTime(message.getUpdateTime());
                messageList.add(msg);
            }
        }
        realm.close();
        return messageList;
    }

    public static void addFavorite(long updateTime) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("updateTime", updateTime).findAll();
        if (realmResults.size() > 0) {
            realm.beginTransaction();
            Message message = realmResults.get(0);
            message.setStatus(1);
            realm.commitTransaction();
        }
        realm.close();
    }

    public static void remove(long updateTime) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("updateTime", updateTime).findAll();
        if (realmResults.size() > 0) {
            realm.beginTransaction();
            realmResults.remove(0);
            realm.commitTransaction();
        }
        realm.close();
    }

    public static void removeHistory() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("status", 0).findAll();
        if (realmResults.size() > 0) {
            realm.beginTransaction();
            realmResults.clear();
            realm.commitTransaction();
        }
        realm.close();
    }

    public static void update(Message msg) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("updateTime", msg.getUpdateTime()).findAll();
        if (realmResults.size() > 0) {
            realm.beginTransaction();
            Message message = realmResults.get(0);
            message.setStatus(msg.getStatus());
            message.setContent(msg.getContent());
            realm.commitTransaction();
        }
        realm.close();
    }

}
