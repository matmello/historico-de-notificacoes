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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.firstsoft.historicodenotificacoes.R;
import br.com.firstsoft.historicodenotificacoes.adapter.PackageRecyclerViewAdapter;
import br.com.firstsoft.historicodenotificacoes.adapter.RecyclerViewAdapter;
import br.com.firstsoft.historicodenotificacoes.model.CNotification;
import br.com.firstsoft.historicodenotificacoes.model.CPackage;

import static br.com.firstsoft.historicodenotificacoes.R.drawable.notification;

public class MainActivity extends AppCompatActivity {

    private NotificationReceiver nReceiver;
    private List<CPackage> packageList;
    private PackageRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        packageList = new ArrayList<>();

        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nReceiver, filter);

        adapter = new PackageRecyclerViewAdapter(packageList, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        Intent i = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("command", "list");
        sendBroadcast(i);
        adapter.clear();
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
            CPackage temp = (CPackage) bundle.getSerializable("PACKAGE");

            if (temp != null) {
                temp.setPackageIcon(getApplicationIcon(temp.getPackageName()));
                adapter.add(temp);
            }
        }
    }

}