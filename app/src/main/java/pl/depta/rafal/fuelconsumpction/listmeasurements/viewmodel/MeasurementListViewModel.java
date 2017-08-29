package pl.depta.rafal.fuelconsumpction.listmeasurements.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.google.common.collect.Lists;

import java.util.List;

import pl.depta.rafal.fuelconsumpction.App;
import pl.depta.rafal.fuelconsumpction.db.AppDatabase;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;


public class MeasurementListViewModel extends ViewModel {

    private LiveData<List<MeasurementEntity>> measurementList;
    private AppDatabase database;

    public LiveData<List<MeasurementEntity>> getMeasurementList() {
        return measurementList;
    }

    public void deleteMeasurement(MeasurementEntity measurementEntity) {
        new DeleteMeasurement(database).execute(measurementEntity);
    }

    public MeasurementListViewModel() {

        database = App.getApp().getDatabase();
        measurementList = database.measurementDao().getAllMeasurement();
    }

    private static class DeleteMeasurement extends AsyncTask<MeasurementEntity, Void, Void> {
        private AppDatabase database;

        DeleteMeasurement(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(MeasurementEntity... measurementEntity) {
            database.measurementDao().deleteMeasurement(measurementEntity[0]);
            return null;
        }
    }

}
