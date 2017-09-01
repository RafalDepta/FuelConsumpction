package pl.depta.rafal.fuelconsumpction.ui.addmeasurement.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.ui.addmeasurement.viewmodel.AddMeasurementViewModel;

public class AddMeasurement extends AppCompatActivity implements LifecycleRegistryOwner {

    @BindView(R.id.measurement_distance)
    TextInputEditText measurementDistance;
    @BindView(R.id.measurement_fuel_price)
    TextInputEditText measurementFuelPrice;
    @BindView(R.id.measurement_fuel_spend)
    TextInputEditText measurementFuelSpend;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private AddMeasurementViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AddMeasurementViewModel.class);

        fab.setOnClickListener(view -> saveMeasurement());
        measurementFuelPrice.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) saveMeasurement();
            return false;
        });
    }

    private void saveMeasurement() {

        measurementDistance.setError(null);
        measurementFuelPrice.setError(null);
        measurementFuelSpend.setError(null);

        String distance = measurementDistance.getText().toString();
        String fuelSpend = measurementFuelSpend.getText().toString();
        String fuelPrice = measurementFuelPrice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(distance)) {
            measurementDistance.setError("Fill section");
            focusView = measurementDistance;
            cancel = true;
        } else if (!viewModel.isTextValid(distance)) {
            measurementDistance.setError("Wrong data");
            focusView = measurementDistance;
            cancel = true;
        }

        if (TextUtils.isEmpty(fuelSpend)) {
            measurementFuelSpend.setError("Fill section");
            focusView = measurementFuelSpend;
            cancel = true;
        } else if (!viewModel.isTextValid(fuelSpend)) {
            measurementFuelSpend.setError("Wrong data");
            focusView = measurementFuelSpend;
            cancel = true;
        }

        if (!viewModel.isTextValid(fuelPrice)) {
            measurementFuelPrice.setError("Wrong data");
            focusView = measurementFuelPrice;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            viewModel.addMeasurement(distance, fuelSpend, fuelPrice);
            finish();
        }


    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}