module com.mikov.car_maintenance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires json.simple;

    opens com.mikov.car_maintenance to javafx.fxml;
    exports com.mikov.car_maintenance;

    exports controller;
    opens controller to javafx.fxml;

    opens datamodel to javafx.fxml;
    exports datamodel;
}