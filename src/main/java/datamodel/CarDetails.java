package datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CarDetails {
    private SimpleIntegerProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty buyDate;
    private SimpleStringProperty expireDate;
    private SimpleIntegerProperty carId;

    public CarDetails() {
        id = new SimpleIntegerProperty(0);
        type = new SimpleStringProperty("");
        buyDate = new SimpleStringProperty("");
        expireDate = new SimpleStringProperty("");
        carId = new SimpleIntegerProperty(0);
    }

    public CarDetails(int id,
                      String type, String buyDate, String expireDate,
                      int carId) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.buyDate = new SimpleStringProperty(buyDate);
        this.expireDate = new SimpleStringProperty(expireDate);
        this.carId = new SimpleIntegerProperty(carId);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getBuyDate() {
        return buyDate.get();
    }

    public void setBuyDate(String buyDate) {
        this.buyDate.set(buyDate);
    }

    public String getExpireDate() {
        return expireDate.get();
    }

    public void setExpireDate(String expireDate) {
        this.expireDate.set(expireDate);
    }

    public int getCarId() {
        return carId.get();
    }

    public void setCarId(int carId) {
        this.carId.set(carId);
    }
}
