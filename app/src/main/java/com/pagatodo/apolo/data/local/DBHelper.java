package com.pagatodo.apolo.data.local;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.pagatodo.apolo.data.local.DBContract.CREATE_TABLE_PROMOTORES_v1;
import static com.pagatodo.apolo.utils.Constants.DATABASE_NAME;
import static com.pagatodo.apolo.utils.Constants.DATABASE_VERSION;

/**
 * Created by jvazquez on 14/02/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    private SQLiteDatabase db = null;
    private String DB_PATH = "/data/data/"+ BuildConfig.APPLICATION_ID + "/databases/";



    public static DBHelper getInstance() {
        if(instance == null)
            instance = new DBHelper(App.getInstance().getApplicationContext());
        return instance;
    }

    public SQLiteDatabase openDB(){
        if(db == null){
            db = getWritableDatabase();
        }else if(!db.isOpen()){
            db = getWritableDatabase();
        }
        return db;
    }
    public void closeDB(){
        if(db != null && db.isOpen())
            db.close();
    }
    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        if(android.os.Build.VERSION.SDK_INT >= 17){
//            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
//        }
//        else {
//            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
//        }
    }

    private DBHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_PROMOTORES_v1());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            //Agregar codigo si se han echo modificaciones a la base de datos y se va a actualizar una versión productiva
        }
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try {
                copyDataBase();
            }
            catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }
    private void copyDataBase() throws IOException
    {
        InputStream mInput = App.getInstance().getApplicationContext().getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }
//    public SQLiteDatabase openDB(){
//        try{
//            String mPath = DB_PATH + DATABASE_NAME;
//            if(db == null){
//                db = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//            }else if(!db.isOpen()){
//                db = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
//            }
//            return db;
//        }catch (Exception e){
//            return db;
//        }
//    }

}
