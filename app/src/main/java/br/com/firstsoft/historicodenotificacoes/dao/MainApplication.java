package br.com.firstsoft.historicodenotificacoes.dao;

import android.app.Application;

import br.com.firstsoft.historicodenotificacoes.dao.Database;

public class MainApplication extends Application {
   public static final String TAG = MainApplication.class.getSimpleName();
   public static Database mDb;


   @Override
   public void onCreate() {
       super.onCreate();
       mDb = new Database(this);
       mDb.open();
   }

   @Override
   public void onTerminate() {
       mDb.close();
       super.onTerminate();
   }

}