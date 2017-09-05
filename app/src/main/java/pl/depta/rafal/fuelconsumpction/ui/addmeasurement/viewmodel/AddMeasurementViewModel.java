package pl.depta.rafal.fuelconsumpction.ui.addmeasurement.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.depta.rafal.fuelconsumpction.App;
import pl.depta.rafal.fuelconsumpction.db.AppDatabase;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;


public class AddMeasurementViewModel extends ViewModel {

    private static final String TAG = "AddMeasurementViewModel";
    private static final String REAL_NUMBER_PATTERN = "(([0-9]+)(\\.[0-9]+)?)|(\\.?[0-9]+)";
    private AppDatabase database;

    private Pattern pattern = Pattern.compile(REAL_NUMBER_PATTERN);


    public AddMeasurementViewModel() {
        database = App.getApp().getDatabase();
    }

    public void addMeasurement(String distance, String fuelSpend, String fuelPrice, int id) {

        MeasurementEntity measurementEntity = new MeasurementEntity();

        measurementEntity.setDistance(convertToFloat(distance));
        measurementEntity.setFuelPrice(convertToFloat(fuelPrice));
        measurementEntity.setFuelSpend(convertToFloat(fuelSpend));

        calculateFuelConsumption(measurementEntity);

        if (id != -1) {
            measurementEntity.setId(id);
            updateMeasurement(measurementEntity);
        } else {
            measurementEntity.setDate(new Date());
            insertMeasurement(measurementEntity);
        }
    }

    public LiveData<MeasurementEntity> getMeasurement(int id) {
        return database.measurementDao().getMeasurement(id);
    }

    private void insertMeasurement(MeasurementEntity measurementEntity) {
        new InsertAsyncTask(database).execute(measurementEntity);
    }

    private void updateMeasurement(MeasurementEntity measurementEntity) {
        new UpdateAsyncTask(database).execute(measurementEntity);
    }

    private void calculateFuelConsumption(MeasurementEntity measurementEntity) {
        float consumption = measurementEntity.getFuelSpend() * 100 / measurementEntity.getDistance();
        measurementEntity.setFuelConsumption(consumption);
    }

    public boolean isTextValid(String text) {

        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    private float convertToFloat(String probablyFloat) {
        try {
            return Float.parseFloat(probablyFloat);
        } catch (NumberFormatException e) {
            Log.d(TAG, "Cannot cast " + probablyFloat + " to float");
            return 0;
        }
    }

    private static class InsertAsyncTask extends AsyncTask<MeasurementEntity, Void, Void> {

        private AppDatabase db;

        InsertAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MeasurementEntity... params) {
            db.measurementDao().insertMeasurement(params[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<MeasurementEntity, Void, Void> {

        private AppDatabase db;

        UpdateAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final MeasurementEntity... params) {
            db.measurementDao().updateMeasurement(params[0]);
            return null;
        }
    }


}
