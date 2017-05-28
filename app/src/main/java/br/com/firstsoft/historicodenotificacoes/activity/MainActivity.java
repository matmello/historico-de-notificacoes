package br.com.firstsoft.historicodenotificacoes.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.firstsoft.historicodenotificacoes.R;
import br.com.firstsoft.historicodenotificacoes.adapter.RecyclerViewAdapter;
import br.com.firstsoft.historicodenotificacoes.model.CNotification;

public class MainActivity extends AppCompatActivity {

    private TextView appName;
    private TextView appTitle;
    private ImageView appIcon;
    private NotificationReceiver nReceiver;
    private List<CNotification> notificationList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        notificationList = new ArrayList<>();

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nReceiver, filter);

        adapter = new RecyclerViewAdapter(notificationList, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void checkPermission() {
        String notificationListenerString = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        if (notificationListenerString == null || !notificationListenerString.contains(getPackageName())) {
            Log.d("Historico", "no access");
            requestPermission();
        } else {
            //Your application has access to the notifications
            Log.d("", "has access");
        }
    }

    private void requestPermission() {
        Intent requestIntent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        requestIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(requestIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    public void buttonClicked(View v) {

        if (v.getId() == R.id.btnListNotify) {
//            Intent i = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
//            i.putExtra("command", "list");
//            sendBroadcast(i);
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
            ncomp.setContentTitle("My Notification");
            ncomp.setContentText("Notification Listener Service Example");
            ncomp.setTicker("Notification Listener Service Example");
            ncomp.setSmallIcon(R.drawable.notification);
            ncomp.setAutoCancel(true);
            nManager.notify((int) System.currentTimeMillis(), ncomp.build());

        }

    }


    class NotificationReceiver extends BroadcastReceiver {

        final PackageManager pm = getApplication().getPackageManager();
        ApplicationInfo ai;

        private Drawable getApplicationIcon(String packageName) {
            try {
                ai = pm.getApplicationInfo(packageName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }

            return (Drawable) (ai != null ? pm.getApplicationIcon(ai) : null);
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            CNotification temp = (CNotification) bundle.getSerializable("NOTIFICATION");
            adapter.add(temp);
            Toast.makeText(MainActivity.this, "ADICIONANDO NOTIFICAÇÃO", Toast.LENGTH_LONG).show();

            if (temp != null) {
                String message = temp.getBigMessage() == null ? "" : "\"" + temp.getBigMessage() + "\"";
                appName.setText(temp.getAppName() + " - " +temp.getTitle());
                appTitle.setText(message);
                appIcon.setImageDrawable(getApplicationIcon(temp.getPackageName()));
            }
        }
    }

}