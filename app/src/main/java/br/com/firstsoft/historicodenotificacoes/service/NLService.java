package br.com.firstsoft.historicodenotificacoes.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.firstsoft.historicodenotificacoes.model.CNotification;
import br.com.firstsoft.historicodenotificacoes.model.CPackage;

import static android.support.v4.app.NotificationCompat.EXTRA_BIG_TEXT;
import static android.support.v4.app.NotificationCompat.EXTRA_INFO_TEXT;
import static android.support.v4.app.NotificationCompat.EXTRA_TEXT;
import static android.support.v4.app.NotificationCompat.EXTRA_TITLE;

/**
 * Created by Danilo on 13/05/2017.
 */

public class NLService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    @Override
    public void onCreate() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        createAuthListener();
        verifyAndLogin();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver, filter);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        myRef = database.getReference("notificacoes");
        myRef.keepSynced(true);
        super.onCreate();
    }

    private void verifyAndLogin() {
        if (user == null) {
            mAuth.signInAnonymously();
        }
    }

    private void createAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
        Date data = new Date();
        String postData = df.format(data);

        CNotification notification = new CNotification(
                getApplicationName(sbn.getPackageName())
                , sbn.getNotification().extras.getString(EXTRA_TITLE)
                , sbn.getNotification().extras.getString(EXTRA_BIG_TEXT)
                , sbn.getPackageName()
                , postData
        );

        String key = myRef.push().getKey();
        myRef.child(user.getUid() + "/" + sbn.getPackageName().replaceAll("\\.", "_") + "/" + key).setValue(notification);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "********** onNOtificationRemoved");
        Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
    }

    private String getApplicationName(String packageName) {

        final PackageManager pm = getApplication().getPackageManager();
        ApplicationInfo ai;

        try {
            ai = pm.getApplicationInfo(packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
    }

    class NLServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            if (intent != null && intent.getStringExtra("command") != null && intent.getStringExtra("command").equals("list")) {
                myRef.child(user.getUid()).orderByChild("data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String packageName = snapshot.getKey().replaceAll("_", "\\.");
                            CPackage cPackage = new CPackage();
                            cPackage.setPackageName(packageName);
                            cPackage.setAppName(getApplicationName(packageName));
                            cPackage.setChildCount(snapshot.getChildrenCount());

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("PACKAGE", cPackage);
                            Intent i2 = new Intent("br.com.firstsoft.historicodenotificacoes.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
                            i2.putExtras(bundle);
                            sendBroadcast(i2);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
