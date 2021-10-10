package controller;

import com.mikov.car_maintenance.Main;
import dataloader.DataFile;
import datamodel.Car;
import datamodel.DataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainBorderPaneLayout;

    // Fields of File Menu
    @FXML
    private MenuItem menuFileSave;
    @FXML
    private MenuItem menuFileClose;
    @FXML
    private MenuItem menuFilePrint;

    // Fields of Edit Menu
    @FXML
    private MenuItem menuEditDelete;

    // Fields of View Menu
    @FXML
    private MenuItem menuViewFullScreen;

    // Fields of Help Menu
    @FXML
    private MenuItem menuHelpViewHelp;
    @FXML
    private MenuItem menuHelpSendFeedback;
    @FXML
    private MenuItem menuHelpAboutCarMaintenance;

    // TableView Properties
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, Integer> idColumn;
    @FXML
    private TableColumn<Car, String> brandColumn;
    @FXML
    private TableColumn<Car, String> yearColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, String> modificationColumn;

    // Fields for adding new car to table
    @FXML
    private ComboBox<String> cmbBrand;
    @FXML
    private ComboBox<String> cmbYear;
    @FXML
    private ComboBox<String> cmbModel;
    @FXML
    private ComboBox<String> cmbModification;

    // Buttons responsible for TableView
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnOpenParts;
    @FXML
    private Button btnOpenDetails;

    private final DataSource dataSource = new DataSource();
    private final DataFile dataFile = new DataFile();
    private ObservableList<Car> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        if (!dataSource.open()) {
            System.out.println("Can't open database!");
            return;
        }

        double tablePrefWidth = carTableView.getPrefWidth();

        double idColumnPrefWidth = tablePrefWidth * 0.050;
        double brandColumnPrefWidth = tablePrefWidth * 0.150;
        double yearColumnPrefWidth = tablePrefWidth * 0.100;
        double modelColumnPrefWidth = tablePrefWidth * 0.300;
        double modificationColumnPrefWidth = tablePrefWidth * 0.395;

        idColumn.setStyle("-fx-alignment: CENTER;");
        yearColumn.setStyle("-fx-alignment: CENTER;");
        brandColumn.setStyle("-fx-alignment: CENTER;");

        idColumn.setPrefWidth(idColumnPrefWidth);
        brandColumn.setPrefWidth(brandColumnPrefWidth);
        yearColumn.setPrefWidth(yearColumnPrefWidth);
        modelColumn.setPrefWidth(modelColumnPrefWidth);
        modificationColumn.setPrefWidth(modificationColumnPrefWidth);

        data = carTableView.getItems();

        ObservableList<Car> dbData = dataSource.getCars();
        if (dbData.size() > 0) {
            data.addAll(dbData);
        }

        cmbBrand.getItems().addAll(dataFile.loadBrand());

        cmbYear.setDisable(true);
        cmbModel.setDisable(true);
        cmbModification.setDisable(true);
    }

    @FXML
    public void handleFileCloseMenuItem(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleBrandVisibilityMode(ActionEvent event) {
        cmbYear.setDisable(false);
        cmbYear.getItems().addAll(dataFile.loadYear());

        if (cmbModel.getItems() != null) {
            cmbModel.getSelectionModel().clearSelection();
            cmbModel.getItems().clear();
            cmbModel.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Model");
                    } else {
                        setText(item);
                    }
                }
            });
            cmbModel.setDisable(true);
        }

        if (cmbModification.getItems() != null) {
            cmbModification.getSelectionModel().clearSelection();
            cmbModification.getItems().clear();
            cmbModification.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Modification");
                    } else {
                        setText(item);
                    }
                }
            });
            cmbModification.setDisable(true);
        }
    }

    @FXML
    public void handleYearVisibilityMode(ActionEvent event) {
        if (cmbYear.getItems() != null) {
            cmbModel.getSelectionModel().clearSelection();
            cmbModel.getItems().clear();
            cmbModel.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Model");
                    } else {
                        setText(item);
                    }
                }
            });
        }

        cmbModel.setDisable(false);
        cmbModel.getItems().addAll(dataFile.loadModel(cmbBrand.getValue()));
    }

    @FXML
    public void handleModelVisibilityMode(ActionEvent event) {
        if (cmbModel.getItems() != null) {
            cmbModification.getSelectionModel().clearSelection();
            cmbModification.getItems().clear();
            cmbModification.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Modification");
                    } else {
                        setText(item);
                    }
                }
            });
        }

        cmbModification.setDisable(false);
        cmbModification.getItems().addAll(dataFile.loadModification(cmbBrand.getValue(), cmbModel.getValue()));
    }

    private void resetCmbBrand() {
        cmbBrand.getSelectionModel().clearSelection();
        cmbBrand.getItems().clear();
        cmbBrand.getItems().addAll(dataFile.loadBrand());
        cmbBrand.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Brand");
                } else {
                    setText(item);
                }
            }
        });
    }

    private void resetCmbYear() {
        cmbYear.getSelectionModel().clearSelection();
        cmbYear.getItems().clear();
        cmbYear.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Year");
                } else {
                    setText(item);
                }
            }
        });
        cmbYear.setDisable(true);
    }

    private void resetCmbModel() {
        cmbModel.getSelectionModel().clearSelection();
        cmbModel.getItems().clear();
        cmbModel.setDisable(true);
    }

    private void resetCmbModification() {
        cmbModification.getSelectionModel().clearSelection();
        cmbModification.getItems().clear();
        cmbModification.setDisable(true);
    }

    private void addEntry() {
        if (cmbBrand.getValue() != null
                && cmbYear.getValue() != null
                && cmbModel.getValue() != null
                && cmbModification.getValue() != null) {
            int tableRowindex = dataSource.getSeqIndex("car") + 1;
            Car car = new Car(tableRowindex, cmbBrand.getValue(), cmbYear.getValue(),
                    cmbModel.getValue(), cmbModification.getValue());

            System.out.println();

            data.add(car);
            dataSource.insertIntoCar(car);

            resetCmbBrand();
            resetCmbYear();
            resetCmbModel();
            resetCmbModification();
        }
    }

    @FXML
    public void handleAddCarBtn() {
        addEntry();
    }

    @FXML
    public void handleDeleteCarBtn() {
        carTableView.setEditable(true);
        if (carTableView.getSelectionModel().getSelectedItem() != null) {
            Car car = carTableView.getSelectionModel().getSelectedItem();
            int tableSelectedIndex = car.getId();
            System.out.println("Selected ID: " + tableSelectedIndex);

            if (tableSelectedIndex >= 0) {
                if (dataSource.deleteFromCar(tableSelectedIndex)) {
                    carTableView.getItems().remove(car);
                    if (carTableView.getItems().size() == 0) {
                        System.out.println("CAR TABLE SIZE: " + carTableView.getItems().size());
                        dataSource.deleteAllFromParts();
                        dataSource.updateSeq(0, "parts");

                        dataSource.deleteAllFromDetails();
                        dataSource.updateSeq(0, "details");
                    }
                    if (carTableView.getItems().size() == 0) {
                        dataSource.updateSeq(0, "car");
                    }
                    if (carTableView.getItems().size() > 0) {
                        Car lastCar = carTableView.getItems().get(carTableView.getItems().size() - 1);
                        dataSource.updateSeq(lastCar.getId(), "car");
                    }
                }
            }
        } else {
            System.out.println("You have to choose row first!");
        }
        carTableView.setEditable(false);
    }

    @FXML
    public void handleOpenPartsBtn(ActionEvent event) {
        try {
            Car car = carTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("parts-view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 950, 400);

            stage.setTitle("Car Maintenance");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMaxWidth(980);
            stage.setMinWidth(980);
            stage.setMaxHeight(450);
            stage.setMinHeight(450);
            PartsController partsController = fxmlLoader.getController();
            if (car != null) {
                partsController.passConnection(dataSource);
                partsController.printCarData(car);
                partsController.tableStartup(car);
                stage.show();
            } else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText("Warning");
                a.setContentText("You have to select car from the table.");
                a.show();
            }
        } catch (IOException e) {
            System.out.println("Couldn't redirect to parts-view.fxml file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOpenDetails() {
        try {
            Car car = carTableView.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("details_view.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load(), 800, 400);

            stage.setTitle("Car Details");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setMaxWidth(900);
            stage.setMinWidth(900);
            stage.setMaxHeight(480);
            stage.setMinHeight(480);

            DetailsController detailsController = fxmlLoader.getController();
            if (car != null) {
                detailsController.passConnection(dataSource);
                detailsController.printCarData(car);
                detailsController.tableStartup(car);
                stage.show();
            } else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText("Warning");
                a.setContentText("You have to select car from the table.");
                a.show();
            }
        } catch (IOException e) {
            System.out.println("Couldn't redirect to parts-view.fxml file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}


