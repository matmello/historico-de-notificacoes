package br.com.firstsoft.historicodenotificacoes.dao;

public interface CNotificationSchema {
    String NOTIFICATION_TABLE = "notification";
    String COLUMN_ID = "_id";
    String COLUMN_APPLICATION_NAME = "application_name";
    String COLUMN_APPLICATION_PACKAGE = "application_package";
    String COLUMN_NOTIFICATION_TITLE = "notification_title";
    String COLUMN_NOTIFICATION_TIME = "notification_time";
    String NOTIFICATION_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + NOTIFICATION_TABLE
         + " (" 
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_APPLICATION_NAME + " TEXT NOT NULL, "
            + COLUMN_APPLICATION_PACKAGE + " TEXT NOT NULL, "
            + COLUMN_NOTIFICATION_TITLE + " TEXT NOT NULL, "
            + COLUMN_NOTIFICATION_TIME + " TEXT NOT NULL"
            + " )";
    String[] NOTIFICATION_COLUMNS = new String[] {COLUMN_ID, COLUMN_APPLICATION_NAME, COLUMN_APPLICATION_PACKAGE, COLUMN_NOTIFICATION_TITLE, COLUMN_NOTIFICATION_TIME};
}