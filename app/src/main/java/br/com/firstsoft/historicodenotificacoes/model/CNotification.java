package br.com.firstsoft.historicodenotificacoes.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Danilo on 13/05/2017.
 */

public class CNotification implements Serializable{

    private String appName;
    private String title;
    private String bigMessage;
    private Bitmap appIcon;
    private String packageName;

    public CNotification(String appName, String title, String bigMessage, String packageName) {
        this.appName = appName;
        this.title = title;
        this.bigMessage = bigMessage;
        this.packageName = packageName;
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

    public Bitmap getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Bitmap appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
