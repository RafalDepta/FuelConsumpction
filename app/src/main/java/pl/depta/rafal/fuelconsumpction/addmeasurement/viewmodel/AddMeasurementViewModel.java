package pl.depta.rafal.fuelconsumpction.addmeasurement.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import pl.depta.rafal.fuelconsumpction.App;
import pl.depta.rafal.fuelconsumpction.db.AppDatabase;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;


public class AddMeasurementViewModel extends ViewModel {

    private AppDatabase database;

    public AddMeasurementViewModel() {
        database = App.getApp().getDatabase();
    }

    public void addMeasurement(MeasurementEntity measurementEntity) {
        new AddAsyncTask(database).execute(measurementEntity);
    }

    public void calculateFuelConsumption(MeasurementEntity measurementEntity) {

        float consumption;

        consumption = measurementEntity.getFuelSpend() * 100 / measurementEntity.getDistance();
        measurementEntity.setFuelConsumption(consumption);
    }

    private static class AddAsyncTask extends AsyncTask<MeasurementEntity, Void, Void> {

        private AppDatabase db;

        AddAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MeasurementEntity... params) {
            db.measurementDao().insertMeasurement(params[0]);
            return null;
        }

    }


}
