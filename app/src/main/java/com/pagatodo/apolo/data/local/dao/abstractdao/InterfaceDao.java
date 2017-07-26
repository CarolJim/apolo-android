package com.pagatodo.apolo.data.local.dao.abstractdao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.pagatodo.apolo.data.local.DBHelper;

import java.util.List;

/**
 * Created by jvazquez on 14/02/2017.
 */

public abstract class InterfaceDao<T, PrimaryKey, VersionLocal> implements DataBaseDao{



    private DBHelper dbHelper;
    private SQLiteDatabase db;
    protected String TABLE_NAME;
    protected String primaryKey;

    public InterfaceDao(){
        dbHelper = DBHelper.getInstance();
    }

    //INSERTS
    public abstract int insertObject(T object);
    public abstract int insertObjects(List<T> objects);

    //GETS
    public abstract T getObjectByPrimaryKey(PrimaryKey key);
    public abstract List<T> getObjects();
    public abstract List<T> findObjects(String query, String... columns);

    //UPDATES
    public abstract int updateObject(T object);
    public abstract int updateObjects(List<T> objects, ResponseDao<VersionLocal> responseDao, String newVersion);

    //DELETES
    public abstract int deleteObjectByPrimaryKey(PrimaryKey key);
    public abstract int deleteObjects(List<PrimaryKey> keys);

    protected abstract ContentValues getContentValues(T object);

    public abstract void onDestroy();

    public String getPrimaryKey(){
        return primaryKey;
    }

    // METODOS DELEGADOS
    @Override
    public void open() {
        db = dbHelper.openDB();
    }

    @Override
    public void close() {
        dbHelper.closeDB();
    }

    @Override
    public Cursor rawQuery(String query) {
        if(db != null && db.isOpen())
            return db.rawQuery(query, null);
        db = dbHelper.openDB();
        return db.rawQuery(query, null);
    }

    @Override
    public Cursor query(String table, String selection, String... selectionArgs) {
        if(db != null && db.isOpen())
            return db.query(table, null, selection, selectionArgs, null, null, null);
        db = dbHelper.openDB();
        return db.query(table, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public Cursor query(String table, String[] columns, String selection, String... selectionArgs) {
        if(db != null && db.isOpen())
            return db.query(table, columns, selection, selectionArgs, null, null, null);
        db = dbHelper.openDB();
        return db.query(table, columns, selection, selectionArgs, null, null, null);
    }

    @Override
    public Cursor query(boolean distinct, String table, String[] columns, String selection, String... selectionArgs) {
        if(db != null && db.isOpen())
            return db.query(distinct, table, columns, selection, selectionArgs, null, null, null, null);
        db = dbHelper.openDB();
        return db.query(distinct, table, columns, selection, selectionArgs, null, null, null, null);
    }

    @Override
    public int insert(String table, ContentValues contentValues) {
        try{
            if(db != null && db.isOpen()){
                return (int) db.insert(table, null, contentValues);
            }
            db = dbHelper.openDB();
            return (int) db.insert(table, null, contentValues);
        }catch (Exception e){
            if(db != null && db.isOpen()){
                return (int) db.insert(table, null, contentValues);
            }
            db = dbHelper.openDB();
            return (int) db.insert(table, null, contentValues);
        }
    }

    @Override
    public int delete(String table, String where) {
        if(db != null && db.isOpen())
            return db.delete(table, where, null);
        db = dbHelper.openDB();
        return db.delete(table, where, null);
    }

    @Override
    public int delete(String table, String where, String... whereArgs) {
        if(db != null && db.isOpen())
            return db.delete(table, where, whereArgs);
        db = dbHelper.openDB();
        return db.delete(table, where, whereArgs);
    }

    @Override
    public int update(String table, ContentValues contentValues, String where) {
        if(db != null && db.isOpen())
            return db.update(table, contentValues, where, null);
        db = dbHelper.openDB();
        return db.update(table, contentValues, where, null);
    }

    @Override
    public int update(String table, ContentValues contentValues, String where, String... whereArgs) {
        if(db != null && db.isOpen())
            return db.update(table, contentValues, where, whereArgs);
        db = dbHelper.openDB();
        return db.update(table, contentValues, where, whereArgs);
    }


    public interface ResponseDao<VersionLocal>{
        void onFinishUpdate(String endPoint, VersionLocal data);
    }

}
