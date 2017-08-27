package pl.depta.rafal.fuelconsumpction.listmeasurements.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import pl.depta.rafal.fuelconsumpction.App;
import pl.depta.rafal.fuelconsumpction.db.AppDatabase;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;
import pl.depta.rafal.fuelconsumpction.model.Measurement;

/**
 * Created by Rafał Depta on 23.08.2017.
 */

public class MeasurementListViewModel extends ViewModel {

    private LiveData<List<MeasurementEntity>> measurementList;
    private AppDatabase database;

    public LiveData<List<MeasurementEntity>> getMeasurementList() {
        return measurementList;
    }

    public void insertMeasurement(MeasurementEntity measurementEntity) {
        new AddMeasurement(database).execute(measurementEntity);
    }

    public MeasurementListViewModel() {

        database = App.getApp().getDatabase();
        measurementList = database.measurementDao().getAllMeasurement();
    }

    private static class AddMeasurement extends AsyncTask<MeasurementEntity, Void, Void> {
        private AppDatabase database;

        public AddMeasurement(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(MeasurementEntity... measurementEntity) {
            database.measurementDao().insertMeasurement(measurementEntity[0]);
            return null;
        }
    }

}
