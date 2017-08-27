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
import pl.depta.rafal.fuelconsumpction.model.Measurement;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Rafał Depta on 23.08.2017.
 */

@Dao
@TypeConverters(DateConverter.class)
public interface MeasurementDao {

    @Query("SELECT * FROM MeasurementEntity")
    LiveData<List<MeasurementEntity>> getAllMeasurement();

    @Insert(onConflict = REPLACE)
    void insertMeasurement(MeasurementEntity measurement);

    @Insert(onConflict = REPLACE)
    void insertAll(List<MeasurementEntity> measurements);

    @Delete
    void deleteMesurement (MeasurementEntity measurement);
}
