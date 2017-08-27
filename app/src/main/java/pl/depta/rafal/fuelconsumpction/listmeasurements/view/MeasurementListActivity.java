package pl.depta.rafal.fuelconsumpction.listmeasurements.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.ButterKnife;
import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.addmeasurement.view.AddMeasurement;
import pl.depta.rafal.fuelconsumpction.databinding.MeasurementListActivityBinding;
import pl.depta.rafal.fuelconsumpction.listmeasurements.viewmodel.MeasurementListViewModel;

public class MeasurementListActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    public static final String TAG = "MeasurementListFragment";

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private MeasurementListActivityBinding mBinding;
    private MeasurementAdapter mMeasurementAdapter;
    private MeasurementListViewModel measurementViewModel;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.measurement_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMeasurementAdapter = new MeasurementAdapter();
        mBinding.measurementList.setAdapter(mMeasurementAdapter);

        fab = findViewById(R.id.add_measurement);
        fab.setOnClickListener(view -> startActivity(new Intent(MeasurementListActivity.this, AddMeasurement.class)));

        measurementViewModel = ViewModelProviders.of(this).get(MeasurementListViewModel.class);
        subscribeUi(measurementViewModel);
    }

    private void subscribeUi(MeasurementListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getMeasurementList().observe(this, measurementEntities -> {
            if (measurementEntities != null) {
                mMeasurementAdapter.setMeasurementList(measurementEntities);
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
