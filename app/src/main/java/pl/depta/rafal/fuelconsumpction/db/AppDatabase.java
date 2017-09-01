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


@Database(entities = {MeasurementEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "measurement_db";


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE measuremententity "
                    + " ADD COLUMN `fuelSpend` REAL NOT NULL default 0");
        }
    };

    public abstract MeasurementDao measurementDao();
}
