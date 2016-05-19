package com.tzf.libo.util;

import java.util.ArrayList;
import java.util.List;


public class DBConstants {

    public final static String DB_PATH = "/sdcard/libo/database";
    public final static String DB_FILE_PATH = "/sdcard/libo/database/libo.db";

    /**
     * 四张基础表类型
     */
    public final static int COMMON_ITEM_SENDTYPE = 1;
    public final static int COMMON_ITEM_MONEYTYPE = 2;
    public final static int COMMON_ITEM_SUBJECT = 3;
    public final static int COMMON_ITEM_REASON = 4;

    /**
     * 数据表公共字段
     */
    public static final String ID = "id";
    public static final String PLACE = "place";
    public static final String SENDTYPE = "sendtype";
    public static final String MONEYTYPE = "moneytype";
    public static final String LIBOSUBJECT = "subject";
    public static final String SENDREASON = "sendreason";
    public static final String MONEY = "money";
    public static final String REMARK = "remark";
    public static final String NAME = "name";

    /**
     * 收入表表名
     */
    public static final String INCOME_TABLE = "income";

    /**
     * 收入表接收时间
     */
    public static final String RECEIVETIME = "receivetime";

    /**
     * 送礼人
     */
    public static final String SENDER = "sender";

    /**
     * 主题
     */
    public static final String SUBJECT = "subject";


    /**
     * 支出表表名
     */
    public static final String EXPENSES_TABLE = "expenses";

    /**
     * 支出表支出时间
     */
    public static final String SENDTIME = "sendtime";

    /**
     * 接收人
     */
    public static final String RECEIVER = "receiver";

    /**
     * 事由
     */
    public static final String REASON = "reason";

    /**
     * 送礼方式表
     */
    public static final String SENDTYPE_TABLE = SENDTYPE;

    /**
     * 礼金方式表
     */
    public static final String MONEYTYPE_TABLE = MONEYTYPE;

    /**
     * 礼薄主题表
     */
    public static final String SUBJECT_TABLE = LIBOSUBJECT;

    /**
     * 消费事由表
     */
    public static final String SENDREASON_TABLE = SENDREASON;

    /**
     * @param 设定文件
     * @return List<String>    返回类型
     * @throws
     * @Title: getCreateTablesSQL
     * @Description: 组建建表SQL
     */
    public static List<String> getCreateTablesSQL() {
        List<String> sqls = new ArrayList<>();
        /** 创建礼薄收入表sql */
        String sql = "create table " + DBConstants.INCOME_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.SENDTIME + " varchar ," + DBConstants.PLACE + " varchar ," + DBConstants.SENDER + " varchar ," + DBConstants.MONEYTYPE + " integer ," + DBConstants.SUBJECT + " integer ," + DBConstants.SENDTYPE + " integer ," + DBConstants.MONEY + " real ," + DBConstants.REMARK + " varchar);";
        sqls.add(sql);
        /** 创建支出表sql */
        sql = "create table " + DBConstants.EXPENSES_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.RECEIVETIME + " varchar ," + DBConstants.PLACE + " varchar ," + DBConstants.RECEIVER + " varchar ," + DBConstants.MONEYTYPE + " integer ," + DBConstants.REASON + " integer ," + DBConstants.SENDTYPE + " integer ," + DBConstants.MONEY + " real ," + DBConstants.REMARK + " varchar);";
        sqls.add(sql);
        /** 创建礼金方式表sql */
        sql = "create table " + DBConstants.MONEYTYPE_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.NAME + " varchar);";
        sqls.add(sql);
        /** 创建送礼方式表sql */
        sql = "create table " + DBConstants.SENDTYPE_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.NAME + " varchar);";
        sqls.add(sql);
        /** 创建礼薄主题表sql */
        sql = "create table " + DBConstants.SUBJECT_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.NAME + " varchar);";
        sqls.add(sql);
        /** 创建送礼事由表sql */
        sql = "create table " + DBConstants.SENDREASON_TABLE + "(" + DBConstants.ID + " integer primary key autoincrement," + DBConstants.NAME + " varchar);";
        sqls.add(sql);

        sql = "insert into " + DBConstants.SUBJECT_TABLE + "(" + DBConstants.NAME + ") values ('结婚典礼')";
        sqls.add(sql);

        sql = "insert into " + DBConstants.SENDREASON_TABLE + "(" + DBConstants.NAME + ") values ('结婚典礼')";
        sqls.add(sql);
        sql = "insert into " + DBConstants.SENDREASON_TABLE + "(" + DBConstants.NAME + ") values ('乔迁之喜')";
        sqls.add(sql);
        sql = "insert into " + DBConstants.SENDREASON_TABLE + "(" + DBConstants.NAME + ") values ('小孩过年红包')";
        sqls.add(sql);

        sql = "insert into " + DBConstants.SENDTYPE_TABLE + "(" + DBConstants.NAME + ") values ('亲自送往')";
        sqls.add(sql);
        sql = "insert into " + DBConstants.SENDTYPE_TABLE + "(" + DBConstants.NAME + ") values ('托人带礼')";
        sqls.add(sql);

        sql = "insert into " + DBConstants.MONEYTYPE_TABLE + "(" + DBConstants.NAME + ") values ('现金')";
        sqls.add(sql);
        sql = "insert into " + DBConstants.MONEYTYPE_TABLE + "(" + DBConstants.NAME + ") values ('红包')";
        sqls.add(sql);

        return sqls;
    }

}
