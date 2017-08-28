package pl.depta.rafal.fuelconsumpction;

import android.app.Application;

import pl.depta.rafal.fuelconsumpction.db.AppDatabase;


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
