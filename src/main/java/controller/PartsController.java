package controller;

import datamodel.Car;
import datamodel.DataSource;
import datamodel.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PartsController {
    @FXML
    private Label lbBrand;
    @FXML
    private Label lbYear;
    @FXML
    private Label lbModel;
    @FXML
    private Label lbModification;
    @FXML
    private Label lbcar_id;

    @FXML
    private TextField textFieldPartInfo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField textFieldPartAddInfo;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;


    @FXML
    private TableView<Part> tableViewParts;
    @FXML
    private TableColumn<Part, Integer> idColumn;
    @FXML
    private TableColumn<Part, String> nameColumn;
    @FXML
    private TableColumn<Part, String> dateColumn;
    @FXML
    private TableColumn<Part, String> additionalInfoColumn;
    @FXML
    private TableColumn<Part, Integer> carId;

    private DataSource dataSource;
    private ObservableList<Part> data = FXCollections.observableArrayList();


    public void printCarData(Car car) {
        lbBrand.setText(car.getBrand());
        lbYear.setText(car.getYear());
        lbModel.setText(car.getModel());
        lbModification.setText(car.getModification());
        lbcar_id.setText(String.valueOf(car.getId()));
    }

    public void tableStartup(Car car) {
        data = tableViewParts.getItems();

        ObservableList<Part> dbData = dataSource.getParts(car.getId());
        if (dbData.size() > 0) {
            data.addAll(dbData);
        }
    }

    public void passConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @FXML
    public void initialize() {
        dateColumn.setStyle("-fx-alignment: CENTER;");
        datePicker.setEditable(false);
        nameColumn.setStyle("-fx-alignment: CENTER;");
        additionalInfoColumn.setStyle("-fx-alignment: CENTER;");
    }

    @FXML
    public void handlePartAdd() {
        if (textFieldPartInfo.getText() != null && datePicker.getValue() != null) {
            int tableRowindex = dataSource.getSeqIndex("parts") + 1;
            String additionalInfo = "";

            if (textFieldPartAddInfo.getText() != null) {
                additionalInfo = textFieldPartAddInfo.getText();
            }

            Part part = new Part(tableRowindex, textFieldPartInfo.getText(), datePicker.getValue(), additionalInfo, Integer.parseInt(lbcar_id.getText()));
            data.add(part);
            dataSource.insertIntoParts(part, Integer.parseInt(lbcar_id.getText()));
            clearForm();
        }
    }

    private void clearForm() {
        textFieldPartInfo.clear();
        textFieldPartAddInfo.clear();
        datePicker.getEditor().clear();
        datePicker.setValue(null);
        datePicker.setEditable(false);
    }

    @FXML
    public void handleDeleteBtn() {
        tableViewParts.setEditable(true);
        if (tableViewParts.getSelectionModel().getSelectedItem() != null) {
            Part part = tableViewParts.getSelectionModel().getSelectedItem();
            int tableSelectedIndex = part.getId();
            System.out.println("Selected ID: " + tableSelectedIndex);

            if (tableSelectedIndex >= 0) {
                if (dataSource.deleteFromParts(tableSelectedIndex)) {
                    tableViewParts.getItems().remove(part);
                    if (tableViewParts.getItems().size() == 0) {
                        dataSource.updateSeq(0, "parts");
                    }
                    if (tableViewParts.getItems().size() > 0) {
                        Part lastPart = tableViewParts.getItems().get(tableViewParts.getItems().size() - 1);
                        dataSource.updateSeq(lastPart.getId(), "parts");
                    }
                }
            }
        } else {
            System.out.println("You have to choose row first!");
        }
        tableViewParts.setEditable(false);
    }
}
