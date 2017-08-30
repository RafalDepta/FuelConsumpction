package pl.depta.rafal.fuelconsumpction.ui.listmeasurements.view;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import pl.depta.rafal.fuelconsumpction.R;
import pl.depta.rafal.fuelconsumpction.databinding.MeasurementItemBinding;
import pl.depta.rafal.fuelconsumpction.model.Measurement;


class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<? extends Measurement> mMeasurementList;

    Measurement getMeasurementAt(int position) {
        return mMeasurementList.get(position);
    }

    void setMeasurementList(final List<? extends Measurement> measurementList) {
        if (mMeasurementList == null) {
            notifyItemRangeInserted(0, measurementList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(mMeasurementList, measurementList));
            result.dispatchUpdatesTo(this);
        }
        mMeasurementList = measurementList;
    }

    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MeasurementItemBinding measurementItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.measurement_item, parent, false);

        return new MeasurementViewHolder(measurementItemBinding);
    }

    @Override
    public void onBindViewHolder(final MeasurementViewHolder holder, int position) {
        holder.binding.setMeasurement(mMeasurementList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mMeasurementList == null ? 0 : mMeasurementList.size();
    }

    class MeasurementViewHolder extends RecyclerView.ViewHolder {
        MeasurementItemBinding binding;

        MeasurementViewHolder(MeasurementItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
