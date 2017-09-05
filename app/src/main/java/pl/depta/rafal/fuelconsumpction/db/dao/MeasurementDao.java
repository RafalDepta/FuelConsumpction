package pl.depta.rafal.fuelconsumpction.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import pl.depta.rafal.fuelconsumpction.db.converter.DateConverter;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
@TypeConverters(DateConverter.class)
public interface MeasurementDao {

    @Query("SELECT * FROM MeasurementEntity")
    LiveData<List<MeasurementEntity>> getAllMeasurement();

    @Query("SELECT * FROM MeasurementEntity WHERE id=:id")
    LiveData<MeasurementEntity> getMeasurement(int id);

    @Insert(onConflict = REPLACE)
    void insertMeasurement(MeasurementEntity measurement);

    @Insert(onConflict = REPLACE)
    void insertAll(List<MeasurementEntity> measurements);

    @Delete
    void deleteMeasurement(MeasurementEntity measurement);
}
