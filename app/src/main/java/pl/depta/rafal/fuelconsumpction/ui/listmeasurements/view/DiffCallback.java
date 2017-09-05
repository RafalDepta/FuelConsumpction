package pl.depta.rafal.fuelconsumpction.ui.listmeasurements.view;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

import pl.depta.rafal.fuelconsumpction.model.Measurement;


public class DiffCallback extends DiffUtil.Callback {
    private List<? extends Measurement> oldList;
    private List<? extends Measurement> newList;

    DiffCallback(List<? extends Measurement> oldList, List<? extends Measurement> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() ==
                newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Measurement measurement = newList.get(newItemPosition);
        Measurement old;
        if (oldItemPosition >= newList.size()) {
            return false;
        } else {
            old = newList.get(oldItemPosition);
            return measurement.getId() == old.getId()
                    && Objects.equals(measurement.getDate(), old.getDate())
                    && Objects.equals(measurement.getDistance(), old.getDistance())
                    && Objects.equals(measurement.getFuelPrice(), old.getFuelPrice())
                    && Objects.equals(measurement.getFuelConsumption(), old.getFuelConsumption());
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        Measurement oldItem = oldList.get(oldItemPosition);
        Measurement newItem = newList.get(newItemPosition);

        if (oldItem.getFuelConsumption() != newItem.getFuelConsumption())
            return newItem;
        else
            return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
