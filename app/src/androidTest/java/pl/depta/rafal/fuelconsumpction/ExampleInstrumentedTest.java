package pl.depta.rafal.fuelconsumpction;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import pl.depta.rafal.fuelconsumpction.db.entity.MeasurementEntity;
import pl.depta.rafal.fuelconsumpction.model.Measurement;
import pl.depta.rafal.fuelconsumpction.ui.listmeasurements.viewmodel.MeasurementListViewModel;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    static Date date;

    @Before
    public void prepareDate() {
        date = new Date();
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("pl.depta.rafal.fuelconsumpction", appContext.getPackageName());
    }

    @Test
    public void testInsert() {
        MeasurementEntity measurement = new MeasurementEntity();
        measurement.setId(88);
        measurement.setFuelSpend(10);
        measurement.setFuelPrice(10);
        measurement.setDistance(10);
        measurement.setDate(date);
        App.getApp().getDatabase().measurementDao().insertMeasurement(measurement);

        LiveData<MeasurementEntity> testSelect = App.getApp().getDatabase().measurementDao().getMeasurement(measurement.getId());
        MeasurementEntity measurementEntity = null;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            measurementEntity = getValue(testSelect);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(10, measurementEntity.getFuelSpend(), Math.abs(10 - measurementEntity.getFuelSpend()));
        assertEquals(10, measurementEntity.getDistance(), Math.abs(10 - measurementEntity.getDistance()));
    }

    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
