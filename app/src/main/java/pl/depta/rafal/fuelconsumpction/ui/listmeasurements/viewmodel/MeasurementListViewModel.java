package pl.depta.rafal.fuelconsumpction.ui.listmeasurements.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import pl.depta.rafal.fuelconsumpction.App;
import pl.depta.rafal.fuelconsumpction.db.AppDatabase;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;


public class MeasurementListViewModel extends ViewModel {

    private LiveData<List<MeasurementEntity>> measurementList;
    private AppDatabase database;
    private MeasurementEntity deletedMeasurement;

    public LiveData<List<MeasurementEntity>> getMeasurementList() {
        return measurementList;
    }

    public MeasurementListViewModel() {

        database = App.getApp().getDatabase();
        measurementList = database.measurementDao().getAllMeasurement();
    }

    public void deleteMeasurement(MeasurementEntity measurementEntity) {
        deletedMeasurement = measurementEntity;
        new DeleteMeasurement(database).execute(measurementEntity);
    }

    public void restoreMeasurement() {
        new InsertMeasurement(database).execute(deletedMeasurement);
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

    private static class InsertMeasurement extends AsyncTask<MeasurementEntity, Void, Void> {

        private AppDatabase db;

        InsertMeasurement(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MeasurementEntity... params) {
            db.measurementDao().insertMeasurement(params[0]);
            return null;
        }

    }


}
