package br.com.firstsoft.historicodenotificacoes.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import br.com.firstsoft.historicodenotificacoes.model.CNotification;

import static android.support.v4.app.NotificationCompat.EXTRA_BIG_TEXT;
import static android.support.v4.app.NotificationCompat.EXTRA_TITLE;

/**
 * Created by Danilo on 13/05/2017.
 */

public class NLServiceaaaaaa extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG, "**********  onNotificationPosted");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("notification_event", "onNotificationPosted :" + sbn.getNotification().tickerText + "\n");
        sendBroadcast(i);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
        Intent i = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("notification_event", "onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver {

        final PackageManager pm = getApplication().getPackageManager();
        ApplicationInfo ai;

        private String getApplicationName(String packageName) {
            try {
                ai = pm.getApplicationInfo(packageName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }
            return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("command") != null) {
                if (intent.getStringExtra("command").equals("clearall")) {
                    NLServiceaaaaaa.this.cancelAllNotifications();
                } else if (intent.getStringExtra("command").equals("list")) {
                    for (StatusBarNotification sbn : NLServiceaaaaaa.this.getActiveNotifications()) {
                        Intent i2 = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");

                        CNotification notification = new CNotification(
                                getApplicationName(sbn.getPackageName())
                                , sbn.getNotification().extras.getString(EXTRA_TITLE)
                                , sbn.getNotification().extras.getString(EXTRA_BIG_TEXT)
                                , sbn.getPackageName()
                            );


                        Bundle bundle = new Bundle();
                        bundle.putSerializable("NOTIFICATION", notification);
                        i2.putExtras(bundle);
                        sendBroadcast(i2);
                    }
                }
            }
        }
    }
}
