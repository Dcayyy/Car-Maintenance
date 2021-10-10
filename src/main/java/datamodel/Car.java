package datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Car {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty brand;
    private final SimpleStringProperty year;
    private final SimpleStringProperty model;
    private final SimpleStringProperty modification;

    public Car() {
        this("", "", "", "");
    }

    public Car(String brand, String year, String model, String modification) {
        this.brand = new SimpleStringProperty(brand);
        this.year = new SimpleStringProperty(year);
        this.model = new SimpleStringProperty(model);
        this.modification = new SimpleStringProperty(modification);
    }

    public Car(Integer id, String brand, String year, String model, String modification) {
        this.id = new SimpleIntegerProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.year = new SimpleStringProperty(year);
        this.model = new SimpleStringProperty(model);
        this.modification = new SimpleStringProperty(modification);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getBrand() {
        return brand.get();
    }

    public String getYear() {
        return year.get();
    }

    public String getModel() {
        return model.get();
    }

    public String getModification() {
        return modification.get();
    }
}
