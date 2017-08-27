package pl.depta.rafal.fuelconsumpction.listmeasurements.view;

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


public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<? extends Measurement> mMeasurementList;

    public MeasurementAdapter() {

    }

    public void setMeasurementList(final List<? extends Measurement> measurementList) {
        if (mMeasurementList == null) {
            mMeasurementList = measurementList;
            notifyItemRangeInserted(0, measurementList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMeasurementList.size();
                }

                @Override
                public int getNewListSize() {
                    return measurementList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMeasurementList.get(oldItemPosition).getId() ==
                            measurementList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Measurement measurement = measurementList.get(newItemPosition);
                    Measurement old = measurementList.get(oldItemPosition);
                    return measurement.getId() == old.getId()
                            && Objects.equals(measurement.getDate(), old.getDate())
                            && Objects.equals(measurement.getDistance(), old.getDistance())
                            && Objects.equals(measurement.getFuelPrice(), old.getFuelPrice())
                            && Objects.equals(measurement.getFuelConsumption(), old.getFuelConsumption());
                }
            });
            mMeasurementList = measurementList;
            result.dispatchUpdatesTo(this);
        }
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
