package com.pagatodo.apolo.data.local.dao.abstractdao;

import android.os.AsyncTask;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by jvazquez on 06/03/2017.
 */

public class AsyncUpdate<Dao extends InterfaceDao, T> extends AsyncTask<Void, Void, String> {

    private Dao dao;
    private InterfaceDao.ResponseDao<String> responseDao;
    private String newVersion;
    private List<T> newObjects;
    private String endPoint;

    public AsyncUpdate(Dao dao, InterfaceDao.ResponseDao<String> responseDao, String newVersion, List<T> newObjects, String endPoint){
        this.dao            = dao;
        this.responseDao    = responseDao;
        this.newVersion     = newVersion;
        this.newObjects     = newObjects;
        this.endPoint       = endPoint;
    }

    @Override
    protected String doInBackground(Void... voids) {
        List<T> oldObjects = dao.getObjects();
        dao.open();
        for(T object: oldObjects){
            Class<?> classOfObject = object.getClass();
            Field[] fields = classOfObject.getFields();
            for(Field field: fields){
                Annotation currentAnotation = field.getAnnotation(PrimaryKey.class);
                if(currentAnotation != null && currentAnotation instanceof PrimaryKey){
                    try {
                        dao.deleteObjectByPrimaryKey(field.get(object));
                        break;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        dao.insertObjects(newObjects);
        dao.close();
        return newVersion;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        responseDao.onFinishUpdate(endPoint, s);
    }
}