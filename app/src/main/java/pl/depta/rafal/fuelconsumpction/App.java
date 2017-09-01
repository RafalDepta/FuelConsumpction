package pl.depta.rafal.fuelconsumpction;

import android.app.Application;
import android.arch.persistence.room.Room;

import pl.depta.rafal.fuelconsumpction.db.AppDatabase;

import static pl.depta.rafal.fuelconsumpction.db.AppDatabase.DATABASE_NAME;
import static pl.depta.rafal.fuelconsumpction.db.AppDatabase.MIGRATION_1_2;


public class App extends Application {

    private static App app;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        database = Room.databaseBuilder(app, AppDatabase.class, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public static App getApp() {
        return app;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
