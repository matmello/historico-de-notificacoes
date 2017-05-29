package br.com.firstsoft.historicodenotificacoes.model;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danilo on 13/05/2017.
 */

public class CNotification implements Serializable, Comparable<CNotification> {

    private String appName;
    private String title;
    private String bigMessage;
    private Drawable appIcon;
    private String packageName;
    private String data;

    public CNotification() {

    }

    public CNotification(String appName, String title, String bigMessage, String packageName, String data) {
        this.appName = appName;
        this.title = title;
        this.bigMessage = bigMessage;
        this.packageName = packageName;
        this.data = data;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBigMessage() {
        return bigMessage;
    }

    public void setBigMessage(String bigMessage) {
        this.bigMessage = bigMessage;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int compareTo(@NonNull CNotification o) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
        try {
            Date data1 = df.parse(this.data);
            Date data2 = df.parse(o.getData());

            if (data1.compareTo(data2) > 0) {
                return 1;
            } else if (data1.compareTo(data2) < 0) {
                return -1;
            } else {
                return 0;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
