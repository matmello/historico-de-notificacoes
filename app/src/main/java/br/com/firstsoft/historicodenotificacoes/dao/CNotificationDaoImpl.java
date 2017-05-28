package br.com.firstsoft.historicodenotificacoes.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

import br.com.firstsoft.historicodenotificacoes.model.CNotification;

import static android.os.Build.ID;

public class CNotificationDaoImpl extends DbContentProvider implements CNotificationDao, CNotificationSchema {
    private Cursor cursor;
    private ContentValues initialValues;

    public CNotificationDaoImpl(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected <T> T cursorToEntity(Cursor cursor) {
        return null;
    }

    @Override
    public CNotification fetchNotificationById(int nofiticationId) {
        return null;
    }

    @Override
    public List<CNotification> fetchAllNotifications() {
        return null;
    }

    @Override
    public boolean addNotification(CNotification notification) {
        return false;
    }

    @Override
    public boolean addNotifications(List<CNotification> notifications) {
        return false;
    }

    @Override
    public boolean deleteAllNotifications() {
        return false;
    }

    @Override
    public boolean deleteUser(CNotification user) {
        return false;
    }
}