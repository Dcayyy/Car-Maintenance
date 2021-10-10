package datamodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Part {
    private SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private LocalDate replaceDate;
    private final SimpleStringProperty additionalInformation;
    private SimpleIntegerProperty carId = new SimpleIntegerProperty();

    public Part() {
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty("");
        additionalInformation = new SimpleStringProperty("");
    }

    public Part(String name, LocalDate replaceDate) {
        this.name = new SimpleStringProperty(name);
        this.replaceDate = replaceDate;
        additionalInformation = new SimpleStringProperty("");
    }

    public Part(String name, LocalDate replaceDate, String additionalInformation) {
        this.name = new SimpleStringProperty(name);
        this.replaceDate = replaceDate;
        this.additionalInformation = new SimpleStringProperty(additionalInformation);
    }

    public Part(int id, String name, LocalDate replaceDate, String additionalInformation, Integer carId) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.replaceDate = replaceDate;
        this.additionalInformation = new SimpleStringProperty(additionalInformation);
        this.carId = new SimpleIntegerProperty(carId);
    }

    public int getCarId() {
        return carId.get();
    }

    public void setCarId(int carId) {
        this.carId.set(carId);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public LocalDate getReplaceDate() {
        return replaceDate;
    }

    public String getAdditionalInformation() {
        return additionalInformation.get();
    }
}
