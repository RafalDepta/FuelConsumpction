package pl.depta.rafal.fuelconsumpction.model;



import java.util.Date;


public interface Measurement {

    int getId();

    Date getDate();

    float getDistance();

    float getFuelPrice();

    float getFuelConsumption();

    float getFuelSpend();
}
