package pl.depta.rafal.fuelconsumpction.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import pl.depta.rafal.fuelconsumpction.db.converter.DateConverter;
import pl.depta.rafal.fuelconsumpction.model.Measurement;


@Entity
public class MeasurementEntity implements Measurement {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @TypeConverters(DateConverter.class)
    private Date date;
    private float distance;
    private float fuelPrice;
    private float fuelConsumption;
    private float fuelSpend;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(float fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public float getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelSpend(float fuelSpend) {
        this.fuelSpend = fuelSpend;
    }

    @Override
    public float getFuelSpend() {
        return fuelSpend;
    }

    public void setFuelConsumption(float fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public MeasurementEntity() {
    }

    public MeasurementEntity(Measurement measurement) {
        this.date = measurement.getDate();
        this.distance = measurement.getDistance();
        this.fuelPrice = measurement.getFuelPrice();
        this.fuelConsumption = measurement.getFuelConsumption();
    }
}
