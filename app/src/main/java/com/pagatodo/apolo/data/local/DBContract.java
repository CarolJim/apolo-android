package com.pagatodo.apolo.data.local;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class DBContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER ";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " PRIMARY KEY(";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private static final String INT_PRIMARY_KEY = " INTEGER NOT NULL PRIMARY KEY "; //AUTOINCREMENT";
    private static final String ALTER_TABLE = " ALTER TABLE ";
    private static final String ADD_COLUMN = " ADD COLUMN ";
    public static final String ISNT_NECESARY = "isnt_necesary";

    private DBContract(){

    }

    /**
     * Tablas
     */
    public static class Promotores{
        public static final String TABLE_NAME = "Promotores";

        public static final String COLUMN_ID_PROMOTOR      = "ID_Promotor";
        public static final String COLUMN_PROMOTOR         = "Promotor";
        public static final String COLUMN_ACTIVO           = "Activo";
        public static final String COLUMN_NOMBRE           = "Nombre";
        public static final String COLUMN_APELLIDOPATERNO  = "ApellidoPaterno";
        public static final String COLUMN_APELLIDOMATERNO  = "ApellidoMaterno";

    }
    /**
     * Metodos para crear las tablas de la versión de la DATABASE_VERSION = 1
     */
    static String CREATE_TABLE_PROMOTORES_v1() {
        return CREATE_TABLE + Promotores.TABLE_NAME + " ( " +
                Promotores.COLUMN_ID_PROMOTOR       + INT_PRIMARY_KEY + COMMA_SEP +
                Promotores.COLUMN_PROMOTOR          + TEXT_TYPE + COMMA_SEP +
                Promotores.COLUMN_ACTIVO            + TEXT_TYPE + COMMA_SEP +
                Promotores.COLUMN_NOMBRE            + TEXT_TYPE + COMMA_SEP +
                Promotores.COLUMN_APELLIDOPATERNO   + TEXT_TYPE + COMMA_SEP +
                Promotores.COLUMN_APELLIDOMATERNO   + TEXT_TYPE + " )";
    }
}
