package pl.depta.rafal.fuelconsumpction.ui.listmeasurements.view;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("dateFormat")
    public static void setDate(TextView view, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String stringDate = sdf.format(date);
        view.setText(stringDate);
    }

    @BindingAdapter("toText")
    public static void convertFloatToText(TextInputEditText view, float number) {
        try {
            view.setText(String.valueOf(number));
        } catch (NumberFormatException e) {
            Log.d("Binding Adapter", e.getMessage());
        }
    }
}
