package pl.depta.rafal.fuelconsumpction.ui.listmeasurements.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.databinding.MeasurementListActivityBinding;
import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;
import pl.depta.rafal.fuelconsumpction.ui.addmeasurement.view.AddMeasurement;
import pl.depta.rafal.fuelconsumpction.ui.listmeasurements.viewmodel.MeasurementListViewModel;

public class MeasurementListActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    public static final String TAG = "MeasurementListFragment";

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private MeasurementAdapter mMeasurementAdapter;
    private MeasurementListViewModel measurementViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MeasurementListActivityBinding mBinding = DataBindingUtil.setContentView(this, R.layout.measurement_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_measurement);
        fab.setOnClickListener(view -> startActivity(new Intent(MeasurementListActivity.this, AddMeasurement.class)));

        mMeasurementAdapter = new MeasurementAdapter();
        mBinding.measurementList.setAdapter(mMeasurementAdapter);

        RecyclerView recyclerView = mBinding.getRoot().findViewById(R.id.measurement_list);
        initSwipe(recyclerView);

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

    private void initSwipe(RecyclerView recyclerView) {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                measurementViewModel.deleteMeasurement((MeasurementEntity) mMeasurementAdapter.getMeasurementAt(position));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
