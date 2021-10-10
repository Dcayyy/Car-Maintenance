package controller;

import datamodel.Car;
import datamodel.CarDetails;
import datamodel.DataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;

public class DetailsController {
    @FXML
    private Label lbcar_id;
    @FXML
    private Label lbBrand;
    @FXML
    private Label lbYear;
    @FXML
    private Label lbModel;
    @FXML
    private Label lbModification;

    @FXML
    private TableView<CarDetails> tableViewDetails;
    @FXML
    private TableColumn<CarDetails, Integer> idColumn;
    @FXML
    private TableColumn<CarDetails, String> typeColumn;
    @FXML
    private TableColumn<CarDetails, String> buyDateColumn;
    @FXML
    private TableColumn<CarDetails, String> expireDateColumn;
    @FXML
    private TableColumn<CarDetails, Integer> carId;

    @FXML
    private DatePicker liabilityInsuranceBuyDate;
    @FXML
    private DatePicker liabilityInsuranceExpireDate;
    @FXML
    private DatePicker vignetteBuyDate;
    @FXML
    private DatePicker vignetteExpireDate;
    @FXML
    private DatePicker technicalReviewBuyDate;
    @FXML
    private DatePicker technicalReviewExpireDate;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;

    private DataSource dataSource;
    private ObservableList<CarDetails> data = FXCollections.observableArrayList();

    public void printCarData(Car car) {
        lbBrand.setText(car.getBrand());
        lbYear.setText(car.getYear());
        lbModel.setText(car.getModel());
        lbModification.setText(car.getModification());
        lbcar_id.setText(String.valueOf(car.getId()));
    }

    public void tableStartup(Car car) {
        data = tableViewDetails.getItems();
        ObservableList<CarDetails> dbData = dataSource.getCarDetails(car.getId());
        if (dbData.size() > 0) {
            data.addAll(dbData);
        }
    }

    public void passConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @FXML
    public void initialize() {
        liabilityInsuranceBuyDate.setEditable(false);
        liabilityInsuranceExpireDate.setEditable(false);
        vignetteBuyDate.setEditable(false);
        vignetteExpireDate.setEditable(false);
        technicalReviewBuyDate.setEditable(false);
        technicalReviewExpireDate.setEditable(false);

        liabilityInsuranceExpireDate.setDisable(true);
        vignetteExpireDate.setDisable(true);
        technicalReviewExpireDate.setDisable(true);

        typeColumn.setStyle("-fx-alignment: CENTER;");
        buyDateColumn.setStyle("-fx-alignment: CENTER;");
        expireDateColumn.setStyle("-fx-alignment: CENTER;");
    }

    @FXML
    private void handleLiabilityInsuranceDatePicker() {
        liabilityInsuranceExpireDate.setDisable(false);
    }

    @FXML
    private void handleVignetteDatePicker() {
        vignetteExpireDate.setDisable(false);
    }

    @FXML
    private void handleTechnicalReviewDatePicker() {
        technicalReviewExpireDate.setDisable(false);
    }

    private void fillTable(CarDetails details, String type, DatePicker buyDate, DatePicker expireDate) {
        details = new CarDetails();
        int tableRowindex = dataSource.getSeqIndex("details") + 1;
        details.setId(tableRowindex);
        details.setType(type);
        details.setBuyDate(buyDate.getValue().toString());
        details.setExpireDate(expireDate.getValue().toString());
        data.add(details);
        dataSource.insertIntoDetails(details, Integer.parseInt(lbcar_id.getText()));
    }

    @FXML
    public void handleBtnAdd() {
        CarDetails details = new CarDetails();
        if (liabilityInsuranceBuyDate.getValue() != null && liabilityInsuranceExpireDate.getValue() != null) {
            fillTable(details, "Liability Insurance", liabilityInsuranceBuyDate, liabilityInsuranceExpireDate);
        }
        if (vignetteExpireDate.getValue() != null && vignetteExpireDate.getValue() != null) {
            fillTable(details, "Vignette", vignetteBuyDate, vignetteExpireDate);
        }
        if (technicalReviewBuyDate.getValue() != null && technicalReviewExpireDate.getValue() != null) {
            fillTable(details, "Technical Review", technicalReviewBuyDate, technicalReviewExpireDate);
        }
        clearForm();
    }

    private void clearDatePicker(DatePicker buyDate, DatePicker expireDate) {
        buyDate.getEditor().clear();
        buyDate.setValue(null);
        expireDate.getEditor().clear();
        expireDate.setValue(null);
        expireDate.setDisable(true);
    }

    private void clearForm() {
        clearDatePicker(liabilityInsuranceBuyDate, liabilityInsuranceExpireDate);
        clearDatePicker(vignetteBuyDate, vignetteExpireDate);
        clearDatePicker(technicalReviewBuyDate, technicalReviewExpireDate);
    }

    @FXML
    public void handleBtnDelete() {
        tableViewDetails.setEditable(true);
        if (tableViewDetails.getSelectionModel().getSelectedItem() != null) {
            CarDetails details = tableViewDetails.getSelectionModel().getSelectedItem();
            int tableSelectedIndex = details.getId();

            if (tableSelectedIndex >= 0) {
                if (dataSource.deleteFromDetails(tableSelectedIndex)) {
                    tableViewDetails.getItems().remove(details);
                    if (tableViewDetails.getItems().size() == 0) {
                        dataSource.updateSeq(0, "details");
                    }
                    if (tableViewDetails.getItems().size() > 0) {
                        CarDetails lastDetails = tableViewDetails.getItems().get(tableViewDetails.getItems().size() - 1);
                        dataSource.updateSeq(lastDetails.getId(), "details");
                    }
                }
            }
        } else {
            System.out.println("You have to choose row first!");
        }
        tableViewDetails.setEditable(false);
    }
}
