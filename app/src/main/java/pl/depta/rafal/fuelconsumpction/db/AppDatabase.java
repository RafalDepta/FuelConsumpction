package pl.depta.rafal.fuelconsumpction.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import java.util.Date;

import pl.depta.rafal.fuelconsumpction.db.dao.MeasurementDao;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;

/**
 * Created by Rafał Depta on 23.08.2017.
 */

@Database(entities = {MeasurementEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "measurement_db")
                            .addMigrations(MIGRATION_1_2)
                            .build();

            //DatabaseCreator.getInstance().fillDatabase();
        }
        return INSTANCE;
    }
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE measuremententity "
                    + " ADD COLUMN `fuelSpend` REAL NOT NULL default 0");
        }
    };

    public abstract MeasurementDao measurementDao();
}
