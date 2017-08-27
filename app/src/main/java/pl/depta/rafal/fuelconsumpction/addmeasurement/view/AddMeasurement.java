package pl.depta.rafal.fuelconsumpction.addmeasurement.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.addmeasurement.viewmodel.AddMeasurementViewModel;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;

public class AddMeasurement extends AppCompatActivity {

    @BindView(R.id.measurement_distance)
    EditText measurementDistance;
    @BindView(R.id.measurement_fuel_price)
    EditText measurementFuelPrice;
    @BindView(R.id.measurement_fuel_spend)
    EditText measurementFuelSpend;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private AddMeasurementViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AddMeasurementViewModel.class);

        fab.setOnClickListener(this::saveMeasurement);
    }

    private void saveMeasurement(View view) {

        if (!measurementDistance.getText().toString().isEmpty() &&
                !measurementFuelSpend.getText().toString().isEmpty()) {

            MeasurementEntity measurementEntity = new MeasurementEntity();

            if (measurementFuelPrice.getText() != null)
                measurementEntity.setFuelPrice(Float.parseFloat(measurementFuelPrice.getText().toString()));

            measurementEntity.setDistance(Float.parseFloat(measurementDistance.getText().toString()));
            measurementEntity.setFuelSpend(Float.parseFloat(measurementFuelSpend.getText().toString()));
            measurementEntity.setDate(new Date());

            viewModel.calculateFuelConsumption(measurementEntity);
            viewModel.addMeasurement(measurementEntity);
            finish();
        } else {
            Snackbar.make(view, "Fill all fields", Snackbar.LENGTH_SHORT).show();
        }

    }

}
