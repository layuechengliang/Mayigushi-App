package com.tzf.libo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mayigushi.common.util.ListUtil;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.model.Income;
import com.tzf.libo.model.InputItem;
import com.tzf.libo.util.DBConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBTableManager {

    private static SQLiteDatabase db = null;

    private static DBTableManager mSingleton = null;

    private DBTableManager() {
    }

    public static DBTableManager getInstance(Context context) {
        if (context == null) {
            return mSingleton;
        }

        if (mSingleton == null) {
            synchronized (DBTableManager.class) {
                if (mSingleton == null) {
                    if (openSQLiteDatabase()) {
                        mSingleton = new DBTableManager();
                    }
                }
            }
        }
        return mSingleton;
    }

    private static boolean openSQLiteDatabase() {
        try {
            boolean isInitTable = false;
            File path = new File(DBConstants.DB_PATH);
            File db_file = new File(DBConstants.DB_FILE_PATH);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!db_file.exists()) {
                isInitTable = true;
                db_file.createNewFile();
            }
            db = SQLiteDatabase.openOrCreateDatabase(db_file, null);
            if (isInitTable) {
                initTable(db);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void initTable(SQLiteDatabase db) {
        List<String> sqls = DBConstants.getCreateTablesSQL();
        if (!ListUtil.isEmpty(sqls)) {
            for (String sql : sqls) {
                db.execSQL(sql);
            }
        }
    }

    public boolean insertCommonItem(int type, String name) {
        boolean flag = false;
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.NAME, name);
        long row = 0;
        switch (type) {
            case DBConstants.COMMON_ITEM_SENDTYPE:
                row = db.insert(DBConstants.SENDTYPE_TABLE, null, cv);
                break;
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                row = db.insert(DBConstants.MONEYTYPE_TABLE, null, cv);
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                row = db.insert(DBConstants.SUBJECT_TABLE, null, cv);
                break;
            case DBConstants.COMMON_ITEM_REASON:
                row = db.insert(DBConstants.SENDREASON_TABLE, null, cv);
                break;
        }
        if (row > 0) flag = true;
        return flag;
    }

    public boolean deleteCommonItem(int type, int id) {
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        switch (type) {
            case DBConstants.COMMON_ITEM_SENDTYPE:
                db.delete(DBConstants.SENDTYPE_TABLE, where, whereValue);
                break;
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                db.delete(DBConstants.MONEYTYPE_TABLE, where, whereValue);
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                db.delete(DBConstants.SUBJECT_TABLE, where, whereValue);
                break;
            case DBConstants.COMMON_ITEM_REASON:
                db.delete(DBConstants.SENDREASON_TABLE, where, whereValue);
                break;
        }
        return true;
    }

    public boolean updateCommonItem(int type, int id, String name) {
        boolean flag = false;
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.NAME, name);
        String table_name = "";
        switch (type) {
            case DBConstants.COMMON_ITEM_SENDTYPE:
                table_name = DBConstants.SENDTYPE_TABLE;
                break;
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                table_name = DBConstants.MONEYTYPE_TABLE;
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                table_name = DBConstants.SUBJECT_TABLE;
                break;
            case DBConstants.COMMON_ITEM_REASON:
                table_name = DBConstants.SENDREASON_TABLE;
                break;
        }

        int row = db.update(table_name, cv, where, whereValue);
        if (row > 0) flag = true;
        return flag;
    }

    public List<InputItem> selectCommonItem(int type) {
        List<InputItem> result = new ArrayList<>();
        InputItem commonItem = null;
        String table_name = "";
        switch (type) {
            case DBConstants.COMMON_ITEM_SENDTYPE:
                table_name = DBConstants.SENDTYPE_TABLE;
                break;
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                table_name = DBConstants.MONEYTYPE_TABLE;
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                table_name = DBConstants.SUBJECT_TABLE;
                break;
            case DBConstants.COMMON_ITEM_REASON:
                table_name = DBConstants.SENDREASON_TABLE;
                break;
        }
        Cursor cursor = db.query(table_name, null, null, null, null, null, " " + DBConstants.ID + " desc");
        while (cursor.moveToNext()) {
            commonItem = new InputItem();
            commonItem.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            commonItem.setName(cursor.getString(cursor.getColumnIndex(DBConstants.NAME)));
            result.add(commonItem);
        }
        cursor.close();
        return result;
    }

    public boolean insertExpenses(Expenses expenses) {
        boolean flag = false;
        ContentValues cv = new ContentValues();

        cv.put(DBConstants.PLACE, expenses.getPlace());
        cv.put(DBConstants.REMARK, expenses.getRemark());
        cv.put(DBConstants.RECEIVER, expenses.getReceiver());
        cv.put(DBConstants.RECEIVETIME, expenses.getReceivetime());
        cv.put(DBConstants.MONEY, expenses.getMoney());
        cv.put(DBConstants.SENDTYPE, expenses.getSendType());
        cv.put(DBConstants.MONEYTYPE, expenses.getMoneyType());
        cv.put(DBConstants.REASON, expenses.getReason());

        long row = db.insert(DBConstants.EXPENSES_TABLE, null, cv);
        if (row > 0) flag = true;
        return flag;
    }

    public boolean deleteExpenses(int id) {
        boolean flag = false;
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        long row = db.delete(DBConstants.EXPENSES_TABLE, where, whereValue);
        if (row > 0) flag = true;
        return flag;
    }

    public boolean updateExpenses(int id, Expenses expenses) {
        boolean flag = false;
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.PLACE, expenses.getPlace());
        cv.put(DBConstants.REMARK, expenses.getRemark());
        cv.put(DBConstants.RECEIVER, expenses.getReceiver());
        cv.put(DBConstants.RECEIVETIME, expenses.getReceivetime());
        cv.put(DBConstants.MONEY, expenses.getMoney());
        cv.put(DBConstants.SENDTYPE, expenses.getSendType());
        cv.put(DBConstants.MONEYTYPE, expenses.getMoneyType());
        cv.put(DBConstants.REASON, expenses.getReason());

        int row = db.update(DBConstants.EXPENSES_TABLE, cv, where, whereValue);
        if (row > 0) {
            flag = true;
        }
        return flag;
    }

    public List<Expenses> selectExpenses() {
        List<Expenses> result = new ArrayList<>();

        Cursor cursor = db.query(DBConstants.EXPENSES_TABLE, null, null, null, null, null, " " + DBConstants.ID + " desc");
        while (cursor.moveToNext()) {
            Expenses expenses = new Expenses();
            expenses.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            expenses.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            expenses.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            expenses.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            expenses.setReason(cursor.getInt(cursor.getColumnIndex(DBConstants.REASON)));
            expenses.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            expenses.setReceiver(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVER)));
            expenses.setReceivetime(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVETIME)));
            expenses.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
            result.add(expenses);
        }
        cursor.close();
        return result;
    }

    public Expenses selectOneExpenses(int id) {
        Expenses expenses = null;
        String selection = DBConstants.ID + "=?";
        String[] selectionArgs = {Integer.toString(id)};

        Cursor cursor = db.query(DBConstants.EXPENSES_TABLE, null, selection, selectionArgs, null, null, " " + DBConstants.ID + " desc");

        if (cursor.moveToNext()) {
            expenses = new Expenses();
            expenses.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            expenses.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            expenses.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            expenses.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            expenses.setReason(cursor.getInt(cursor.getColumnIndex(DBConstants.REASON)));
            expenses.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            expenses.setReceiver(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVER)));
            expenses.setReceivetime(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVETIME)));
            expenses.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
        }

        cursor.close();
        return expenses;
    }

    public boolean insertIncome(Income income) {
        boolean flag = false;
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.PLACE, income.getPlace());
        cv.put(DBConstants.REMARK, income.getRemark());
        cv.put(DBConstants.SENDER, income.getSender());
        cv.put(DBConstants.SENDTIME, income.getSendtime());
        cv.put(DBConstants.MONEY, income.getMoney());
        cv.put(DBConstants.SENDTYPE, income.getSendType());
        cv.put(DBConstants.MONEYTYPE, income.getMoneyType());
        cv.put(DBConstants.SUBJECT, income.getSubject());

        long row = db.insert(DBConstants.INCOME_TABLE, null, cv);
        if (row > 0) {
            flag = true;
        }
        return flag;
    }

    public boolean deleteIncome(int id) {
        boolean flag = false;
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        long row = db.delete(DBConstants.INCOME_TABLE, where, whereValue);
        if (row > 0) flag = true;
        return flag;
    }

    public boolean updateIncome(int id, Income income) {
        boolean flag = false;
        String where = DBConstants.ID + "=?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put(DBConstants.PLACE, income.getPlace());
        cv.put(DBConstants.REMARK, income.getRemark());
        cv.put(DBConstants.SENDER, income.getSender());
        cv.put(DBConstants.SENDTIME, income.getSendtime());
        cv.put(DBConstants.MONEY, income.getMoney());
        cv.put(DBConstants.SENDTYPE, income.getSendType());
        cv.put(DBConstants.MONEYTYPE, income.getMoneyType());
        cv.put(DBConstants.SUBJECT, income.getSubject());

        int row = db.update(DBConstants.INCOME_TABLE, cv, where, whereValue);

        if (row > 0) {
            flag = true;
        }
        return flag;
    }

    public List<Income> selectIncome() {
        List<Income> result = new ArrayList<>();
        Cursor cursor = db.query(DBConstants.INCOME_TABLE, null, null, null, null, null, " " + DBConstants.ID + " desc");
        while (cursor.moveToNext()) {
            Income income = new Income();
            income.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            income.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            income.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            income.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            income.setSubject(cursor.getInt(cursor.getColumnIndex(DBConstants.SUBJECT)));
            income.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            income.setSender(cursor.getString(cursor.getColumnIndex(DBConstants.SENDER)));
            income.setSendtime(cursor.getString(cursor.getColumnIndex(DBConstants.SENDTIME)));
            income.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
            result.add(income);
        }

        cursor.close();
        return result;
    }

    public Income selectOneIncome(int id) {
        Income income = null;
        String selection = DBConstants.ID + "=?";
        String[] selectionArgs = {Integer.toString(id)};

        Cursor cursor = db.query(DBConstants.INCOME_TABLE, null, selection, selectionArgs, null, null, " " + DBConstants.ID + " desc");

        if (cursor.moveToNext()) {
            income = new Income();
            income.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            income.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            income.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            income.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            income.setSubject(cursor.getInt(cursor.getColumnIndex(DBConstants.SUBJECT)));
            income.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            income.setSender(cursor.getString(cursor.getColumnIndex(DBConstants.SENDER)));
            income.setSendtime(cursor.getString(cursor.getColumnIndex(DBConstants.SENDTIME)));
            income.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
        }

        cursor.close();
        return income;
    }

    private boolean checkSubjectExist(int subject_id) {
        boolean result = false;
        if (subject_id <= 0) {
            return false;
        }
        String sql = "select count(*) as c from " + DBConstants.INCOME_TABLE + " where " + DBConstants.SUBJECT + " = " + subject_id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        cursor.close();
        return result;
    }

    private boolean checkReasonExist(int reason_id) {
        boolean result = false;
        if (reason_id <= 0) {
            return false;
        }
        String sql = "select count(*) as c from " + DBConstants.EXPENSES_TABLE + " where " + DBConstants.REASON + " = " + reason_id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        cursor.close();
        return result;
    }

    private boolean checkSendTypeExist(int sendType_id) {
        boolean result = false;
        if (sendType_id <= 0) {
            return false;
        }
        String sql = "select count(*) as c from " + DBConstants.EXPENSES_TABLE + " where " + DBConstants.SENDTYPE + " = " + sendType_id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        sql = "select count(*) as c from " + DBConstants.INCOME_TABLE + " where " + DBConstants.SENDTYPE + " = " + sendType_id;
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        cursor.close();
        return result;
    }

    private boolean checkMoneyTypeExist(int moneyType_id) {
        boolean result = false;
        if (moneyType_id <= 0) {
            return false;
        }

        String sql = "select count(*) as c from " + DBConstants.EXPENSES_TABLE + " where " + DBConstants.MONEYTYPE + " = " + moneyType_id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        sql = "select count(*) as c from " + DBConstants.INCOME_TABLE + " where " + DBConstants.MONEYTYPE + " = " + moneyType_id;
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                result = true;
            }
        }

        cursor.close();
        return result;
    }

    public boolean checkInputItemDelete(int type, int id) {
        boolean result = true;
        switch (type) {
            case DBConstants.COMMON_ITEM_SENDTYPE:
                result = !checkSendTypeExist(id);
                break;
            case DBConstants.COMMON_ITEM_MONEYTYPE:
                result = !checkMoneyTypeExist(id);
                break;
            case DBConstants.COMMON_ITEM_SUBJECT:
                result = !checkSubjectExist(id);
                break;
            case DBConstants.COMMON_ITEM_REASON:
                result = !checkReasonExist(id);
                break;
        }
        return result;
    }

    public List<String> selectExpensesName(String name) {
        List<String> result = new ArrayList<>();
        String sql = "select DISTINCT receiver from " + DBConstants.EXPENSES_TABLE + " where receiver like '%" + name + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVER)));
        }
        cursor.close();
        return result;
    }

    public List<String> selectIncomeName(String name) {
        List<String> result = new ArrayList<>();
        String sql = "select DISTINCT sender from " + DBConstants.INCOME_TABLE + " where sender like '%" + name + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(DBConstants.SENDER)));
        }
        cursor.close();
        return result;
    }

    public List<Expenses> selectExpensesByName(String name) {
        List<Expenses> result = new ArrayList<>();
        String selection = DBConstants.RECEIVER + "=?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(DBConstants.EXPENSES_TABLE, null, selection, selectionArgs, null, null, " " + DBConstants.ID + " desc");
        while (cursor.moveToNext()) {
            Expenses expenses = new Expenses();
            expenses.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            expenses.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            expenses.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            expenses.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            expenses.setReason(cursor.getInt(cursor.getColumnIndex(DBConstants.REASON)));
            expenses.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            expenses.setReceiver(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVER)));
            expenses.setReceivetime(cursor.getString(cursor.getColumnIndex(DBConstants.RECEIVETIME)));
            expenses.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
            result.add(expenses);
        }
        cursor.close();
        return result;
    }

    public List<Income> selectIncomeByName(String name) {
        List<Income> result = new ArrayList<>();
        String selection = DBConstants.SENDER + "=?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(DBConstants.INCOME_TABLE, null, selection, selectionArgs, null, null, " " + DBConstants.ID + " desc");
        while (cursor.moveToNext()) {
            Income income = new Income();
            income.setId(cursor.getInt(cursor.getColumnIndex(DBConstants.ID)));
            income.setMoney(cursor.getFloat(cursor.getColumnIndex(DBConstants.MONEY)));
            income.setMoneyType(cursor.getInt(cursor.getColumnIndex(DBConstants.MONEYTYPE)));
            income.setSendType(cursor.getInt(cursor.getColumnIndex(DBConstants.SENDTYPE)));
            income.setSubject(cursor.getInt(cursor.getColumnIndex(DBConstants.SUBJECT)));
            income.setPlace(cursor.getString(cursor.getColumnIndex(DBConstants.PLACE)));
            income.setSender(cursor.getString(cursor.getColumnIndex(DBConstants.SENDER)));
            income.setSendtime(cursor.getString(cursor.getColumnIndex(DBConstants.SENDTIME)));
            income.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.REMARK)));
            result.add(income);
        }
        cursor.close();
        return result;
    }

}
