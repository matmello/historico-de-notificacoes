package br.com.firstsoft.historicodenotificacoes.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.firstsoft.historicodenotificacoes.dao.CNotificationSchema;

public class Database {
    private static final String TAG = "NotificationsDB";
    private static final String DATABASE_NAME = "notifications_db";
    private DatabaseHelper mDbHelper;
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static CNotificationDaoImpl mNotificationDao;

    public Database(Context context) {
        this.mContext = context;
    }

    public Database open() throws SQLException {
       mDbHelper = new DatabaseHelper(mContext);
       SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        mNotificationDao = new CNotificationDaoImpl(mDb);

       return this;
   }

   public void close() {
       mDbHelper.close();
   }

   private static class DatabaseHelper extends SQLiteOpenHelper {
       DatabaseHelper(Context context) {
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db) {
           db.execSQL(CNotificationSchema.NOTIFICATION_TABLE_CREATE);
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion,
          int newVersion) {
           Log.w(TAG, "Upgrading database from version "
                + oldVersion + " to "
                + newVersion + " which destroys all old data");

           db.execSQL("DROP TABLE IF EXISTS " 
                + CNotificationSchema.NOTIFICATION_TABLE);
           onCreate(db);

       }
   }

}