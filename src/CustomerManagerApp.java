package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManagerApp extends Application {

    // The data model
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    // UI components
    private Stage primaryStage;
    private TextField nameField;
    private ComboBox<String> provinceBox;
    private TableView<Customer> tableView;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // --- Form ---
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(0, 0, 20, 0));

        Label nameLabel = new Label("_Name:"); // Alt+N
        nameLabel.setMnemonicParsing(true);
        nameField = new TextField();
        nameField.setPromptText("Enter Customer Name");
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("_Province:"); // Alt+P
        provinceLabel.setMnemonicParsing(true);
        provinceBox = new ComboBox<>();
        provinceBox.setPromptText("Select Province");
        provinceBox.getItems().addAll(
                "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western"
        );
        provinceLabel.setLabelFor(provinceBox);

        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(provinceLabel, 0, 1);
        formGrid.add(provinceBox, 1, 1);

        // --- Buttons ---
        Button addButton = new Button("Add Customer");
        addButton.setDefaultButton(true); // Enter key
        addButton.setOnAction(e -> addCustomer());

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> deleteCustomer());

        HBox buttonBox = new HBox(10, addButton, deleteButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        root.setTop(new VBox(10, formGrid, buttonBox));

        // --- TableView (lambdas instead of PropertyValueFactory) ---
        tableView = new TableView<>(customerList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tableView.setPlaceholder(new Label("No customers added yet."));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());

        TableColumn<Customer, String> provCol = new TableColumn<>("Province");
        provCol.setCellValueFactory(cell -> cell.getValue().provinceProperty());

        tableView.getColumns().addAll(nameCol, provCol);
        root.setCenter(tableView);

        // --- Scene ---
        stage.setTitle("Customer Manager Lab");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        nameField.requestFocus();
    }

    /** Validate input, then add the customer. */
    private void addCustomer() {
        String name = nameField.getText() == null ? "" : nameField.getText().trim();
        String province = provinceBox.getValue();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Name cannot be empty.");
            nameField.requestFocus();
            return;
        }
        if (province == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a province.");
            provinceBox.requestFocus();
            return;
        }

        customerList.add(new Customer(name, province));

        nameField.clear();
        provinceBox.getSelectionModel().clearSelection();
        provinceBox.setValue(null); // ensures the prompt text shows again
        nameField.requestFocus();
    }

    /** Confirm, then delete the selected customer. */
    private void deleteCustomer() {
        Customer selected = tableView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a customer to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.initOwner(primaryStage);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete Customer");
        confirm.setContentText("Are you sure you want to delete " + selected.getName() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                customerList.remove(selected);
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}