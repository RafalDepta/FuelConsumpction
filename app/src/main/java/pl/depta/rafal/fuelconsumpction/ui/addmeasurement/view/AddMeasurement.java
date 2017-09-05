package pl.depta.rafal.fuelconsumpction.ui.addmeasurement.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.databinding.ActivityAddMeasurementBinding;
import pl.depta.rafal.fuelconsumpction.ui.addmeasurement.viewmodel.AddMeasurementViewModel;

public class AddMeasurement extends AppCompatActivity implements LifecycleRegistryOwner {

/*    @BindView(R.id.measurement_distance)
    TextInputEditText measurementDistance;
    @BindView(R.id.measurement_fuel_price)
    TextInputEditText measurementFuelPrice;
    @BindView(R.id.measurement_fuel_spend)
    TextInputEditText measurementFuelSpend;
    @BindView(R.id.fab)
    FloatingActionButton fab;*/

    public static final String MEASUREMENT_BUNDLE = "measurement_bundle";

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private AddMeasurementViewModel viewModel;
    private ActivityAddMeasurementBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_measurement);

        setSupportActionBar(mBinding.toolbar);
        //ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(AddMeasurementViewModel.class);

        int measurementId = getIntent().getIntExtra(MEASUREMENT_BUNDLE, -1);
        if (measurementId != -1) {
            viewModel.getMeasurement(measurementId).observe(this, measurementEntity -> mBinding.setMeasurement(measurementEntity));
        }

        mBinding.fab.setOnClickListener(view -> saveMeasurement(measurementId));
        mBinding.measurementFuelPrice.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) saveMeasurement(measurementId);
            return false;
        });
    }

    private void saveMeasurement(int id) {

        mBinding.measurementDistance.setError(null);
        mBinding.measurementFuelPrice.setError(null);
        mBinding.measurementFuelSpend.setError(null);

        String distance = mBinding.measurementDistance.getText().toString();
        String fuelSpend = mBinding.measurementFuelSpend.getText().toString();
        String fuelPrice = mBinding.measurementFuelPrice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(distance)) {
            mBinding.measurementDistance.setError("Fill section");
            focusView = mBinding.measurementDistance;
            cancel = true;
        } else if (!viewModel.isTextValid(distance)) {
            mBinding.measurementDistance.setError("Wrong data");
            focusView = mBinding.measurementDistance;
            cancel = true;
        }

        if (TextUtils.isEmpty(fuelSpend)) {
            mBinding.measurementFuelSpend.setError("Fill section");
            focusView = mBinding.measurementFuelSpend;
            cancel = true;
        } else if (!viewModel.isTextValid(fuelSpend)) {
            mBinding.measurementFuelSpend.setError("Wrong data");
            focusView = mBinding.measurementFuelSpend;
            cancel = true;
        }

        if (!viewModel.isTextValid(fuelPrice)) {
            mBinding.measurementFuelPrice.setError("Wrong data");
            focusView = mBinding.measurementFuelPrice;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            viewModel.addMeasurement(distance, fuelSpend, fuelPrice, id);
            finish();
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
