package com.pagatodo.apolo.data.local.dao.abstractdao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by jvazquez on 14/02/2017.
 */

public interface DataBaseDao {
        void open();
        void close();
        Cursor rawQuery(String query);
        Cursor query(String table, String selection, String... selectionArgs);
        Cursor query(String table, String[] columns, String selection, String... selectionArgs);
        Cursor query(boolean distinct, String table, String[] columns, String selection, String... selectionArgs);
        int insert(String table, ContentValues contentValues);
        int delete(String table, String where);
        int delete(String table, String where, String... whereArgs);
        int update(String table, ContentValues contentValues, String where);
        int update(String table, ContentValues contentValues, String where, String... whereArgs);
}
