package pl.depta.rafal.fuelconsumpction;

import android.app.Application;

import pl.depta.rafal.fuelconsumpction.db.AppDatabase;

/**
 * Created by Rafał Depta on 23.08.2017.
 */

public class App extends Application {

    private static App app;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        database = AppDatabase.getDatabase(this);
    }

    public static App getApp() {
        return app;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
