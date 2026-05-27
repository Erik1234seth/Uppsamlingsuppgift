package se.lu.ics.vikingexpress.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import se.lu.ics.vikingexpress.controller.*;
import se.lu.ics.vikingexpress.model.*;

import java.time.LocalDate;
import java.util.Optional;

public class MainView extends BorderPane {

    private final WarehouseController warehouseController;
    private final ShipmentController shipmentController;
    private final InspectionController inspectionController;
    private final ShipmentLogController logController;

    private final ObservableList<Warehouse> warehouseList;
    private final ObservableList<Shipment> shipmentList;
    private final ObservableList<Inspection> inspectionList;
    private final ObservableList<ShipmentLog> logList;

    private TableView<Warehouse> warehouseTable;
    private TableView<Shipment> shipmentTable;
    private TableView<Inspection> inspectionTable;
    private TableView<ShipmentLog> logTable;

    private Warehouse selectedWarehouse;
    private Shipment selectedShipment;

    public MainView(WarehouseController warehouseController,
                    ShipmentController shipmentController,
                    InspectionController inspectionController,
                    ShipmentLogController logController) {
        this.warehouseController = warehouseController;
        this.shipmentController = shipmentController;
        this.inspectionController = inspectionController;
        this.logController = logController;

        warehouseList = FXCollections.observableArrayList(warehouseController.getAllWarehouses());
        shipmentList = FXCollections.observableArrayList();
        inspectionList = FXCollections.observableArrayList();
        logList = FXCollections.observableArrayList();

        buildUI();
    }

    private void buildUI() {
        Label title = new Label("VikingExpress - Inventory Management System");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        BorderPane.setMargin(title, new Insets(12, 15, 8, 15));
        setTop(title);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(
                buildWarehouseSection(),
                buildShipmentSection(),
                buildDetailSection()
        );
        splitPane.setDividerPositions(0.34, 0.67);
        setCenter(splitPane);
    }

    // ================================================================
    //  WAREHOUSE SECTION
    // ================================================================

    private VBox buildWarehouseSection() {
        Label sectionLabel = new Label("Warehouses");
        sectionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        warehouseTable = buildWarehouseTable();
        warehouseTable.setItems(warehouseList);
        warehouseTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> onWarehouseSelected(newVal));

        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);

        warehouseTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            editBtn.setDisable(n == null);
            deleteBtn.setDisable(n == null);
        });

        addBtn.setOnAction(e -> handleAddWarehouse());
        editBtn.setOnAction(e -> handleEditWarehouse());
        deleteBtn.setOnAction(e -> handleDeleteWarehouse());

        HBox buttons = new HBox(5, addBtn, editBtn, deleteBtn);
        buttons.setPadding(new Insets(5, 0, 0, 0));

        VBox vbox = new VBox(6, sectionLabel, warehouseTable, buttons);
        VBox.setVgrow(warehouseTable, Priority.ALWAYS);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    @SuppressWarnings("unchecked")
    private TableView<Warehouse> buildWarehouseTable() {
        TableView<Warehouse> table = new TableView<>();

        TableColumn<Warehouse, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        nameCol.setMinWidth(110);

        TableColumn<Warehouse, String> regionCol = new TableColumn<>("Region");
        regionCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRegion().toString()));
        regionCol.setMinWidth(60);

        TableColumn<Warehouse, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAddress()));
        addressCol.setMinWidth(140);

        TableColumn<Warehouse, Integer> capacityCol = new TableColumn<>("Cap.");
        capacityCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getCapacity()));
        capacityCol.setMinWidth(45);

        TableColumn<Warehouse, Integer> stockCol = new TableColumn<>("Stock");
        stockCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getStockLevel()));
        stockCol.setMinWidth(45);

        TableColumn<Warehouse, String> lastInspCol = new TableColumn<>("Last Inspection");
        lastInspCol.setCellValueFactory(d -> {
            LocalDate date = d.getValue().getLastInspectionDate();
            return new SimpleStringProperty(date != null ? date.toString() : "None");
        });
        lastInspCol.setMinWidth(100);

        table.getColumns().addAll(nameCol, regionCol, addressCol, capacityCol, stockCol, lastInspCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    private void onWarehouseSelected(Warehouse warehouse) {
        selectedWarehouse = warehouse;
        selectedShipment = null;
        shipmentTable.getSelectionModel().clearSelection();
        inspectionList.clear();
        logList.clear();
        shipmentList.setAll(warehouse != null ? warehouse.getShipments() : FXCollections.emptyObservableList());
    }

    private void handleAddWarehouse() {
        showWarehouseDialog(null);
    }

    private void handleEditWarehouse() {
        Warehouse selected = warehouseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showWarehouseDialog(selected);
        }
    }

    private void handleDeleteWarehouse() {
        Warehouse selected = warehouseTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        warehouseController.removeWarehouse(selected);
        warehouseList.remove(selected);
        shipmentList.clear();
        inspectionList.clear();
        logList.clear();
    }

    private void showWarehouseDialog(Warehouse existing) {
        boolean isEdit = existing != null;
        Dialog<ButtonType> dialog = createDialog(isEdit ? "Edit Warehouse" : "Add Warehouse");

        GridPane grid = createFormGrid();
        TextField nameField = new TextField(isEdit ? existing.getName() : "");
        TextField addressField = new TextField(isEdit ? existing.getAddress() : "");
        ComboBox<WarehouseRegion> regionBox = new ComboBox<>(
                FXCollections.observableArrayList(WarehouseRegion.values()));
        regionBox.setValue(isEdit ? existing.getRegion() : WarehouseRegion.NORTH);
        TextField capacityField = new TextField(isEdit ? String.valueOf(existing.getCapacity()) : "");

        setFormWidth(200, nameField, addressField, regionBox, capacityField);
        addFormRow(grid, 0, "Name:", nameField);
        addFormRow(grid, 1, "Address:", addressField);
        addFormRow(grid, 2, "Region:", regionBox);
        addFormRow(grid, 3, "Capacity:", capacityField);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        WarehouseRegion region = regionBox.getValue();
        int capacity;

        try {
            capacity = Integer.parseInt(capacityField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Capacity must be a valid whole number.");
            return;
        }

        try {
            if (isEdit) {
                warehouseController.updateWarehouse(existing, name, address, region, capacity);
                warehouseTable.refresh();
            } else {
                Warehouse newWarehouse = warehouseController.addWarehouse(name, address, region, capacity);
                warehouseList.add(newWarehouse);
                warehouseTable.getSelectionModel().select(newWarehouse);
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // ================================================================
    //  SHIPMENT SECTION
    // ================================================================

    private VBox buildShipmentSection() {
        Label sectionLabel = new Label("Shipments");
        sectionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        shipmentTable = buildShipmentTable();
        shipmentTable.setItems(shipmentList);
        shipmentTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> onShipmentSelected(newVal));

        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);

        warehouseTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->
                addBtn.setDisable(n == null));
        shipmentTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            editBtn.setDisable(n == null);
            deleteBtn.setDisable(n == null);
        });

        addBtn.setOnAction(e -> handleAddShipment());
        editBtn.setOnAction(e -> handleEditShipment());
        deleteBtn.setOnAction(e -> handleDeleteShipment());

        HBox buttons = new HBox(5, addBtn, editBtn, deleteBtn);
        buttons.setPadding(new Insets(5, 0, 0, 0));

        VBox vbox = new VBox(6, sectionLabel, shipmentTable, buttons);
        VBox.setVgrow(shipmentTable, Priority.ALWAYS);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    @SuppressWarnings("unchecked")
    private TableView<Shipment> buildShipmentTable() {
        TableView<Shipment> table = new TableView<>();

        TableColumn<Shipment, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getId()));
        idCol.setMinWidth(75);

        TableColumn<Shipment, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDescription()));
        descCol.setMinWidth(150);

        TableColumn<Shipment, String> dirCol = new TableColumn<>("Direction");
        dirCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().isIncoming() ? "Incoming" : "Outgoing"));
        dirCol.setMinWidth(80);

        TableColumn<Shipment, String> inspCol = new TableColumn<>("Inspected");
        inspCol.setCellValueFactory(d -> new SimpleStringProperty(
                d.getValue().isInspected() ? "Yes" : "No"));
        inspCol.setMinWidth(70);

        table.getColumns().addAll(idCol, descCol, dirCol, inspCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    private void onShipmentSelected(Shipment shipment) {
        selectedShipment = shipment;
        if (shipment != null) {
            inspectionList.setAll(shipment.getInspections());
            logList.setAll(shipment.getLogs());
        } else {
            inspectionList.clear();
            logList.clear();
        }
    }

    private void handleAddShipment() {
        if (selectedWarehouse == null) return;
        showShipmentDialog(null);
    }

    private void handleEditShipment() {
        Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showShipmentDialog(selected);
        }
    }

    private void handleDeleteShipment() {
        Shipment selected = shipmentTable.getSelectionModel().getSelectedItem();
        if (selected == null || selectedWarehouse == null) return;
        shipmentController.removeShipment(selectedWarehouse, selected);
        shipmentList.remove(selected);
        inspectionList.clear();
        logList.clear();
        warehouseTable.refresh();
    }

    private void showShipmentDialog(Shipment existing) {
        boolean isEdit = existing != null;
        Dialog<ButtonType> dialog = createDialog(isEdit ? "Edit Shipment" : "Add Shipment");

        GridPane grid = createFormGrid();
        TextField descField = new TextField(isEdit ? existing.getDescription() : "");
        ComboBox<String> dirBox = new ComboBox<>(FXCollections.observableArrayList("Incoming", "Outgoing"));
        dirBox.setValue(isEdit ? (existing.isIncoming() ? "Incoming" : "Outgoing") : "Incoming");

        setFormWidth(220, descField, dirBox);
        addFormRow(grid, 0, "Description:", descField);
        addFormRow(grid, 1, "Direction:", dirBox);

        if (!isEdit) {
            Label note = new Label("Shipment ID will be automatically generated.");
            note.setStyle("-fx-font-style: italic; -fx-font-size: 11px;");
            grid.add(note, 0, 2, 2, 1);
        }

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        String description = descField.getText().trim();
        boolean isIncoming = "Incoming".equals(dirBox.getValue());

        try {
            if (isEdit) {
                shipmentController.updateShipment(existing, description, isIncoming);
                shipmentTable.refresh();
            } else {
                Shipment newShipment = shipmentController.addShipment(selectedWarehouse, description, isIncoming);
                shipmentList.add(newShipment);
                shipmentTable.getSelectionModel().select(newShipment);
                warehouseTable.refresh();
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // ================================================================
    //  DETAIL SECTION (tabs: Inspections + Warehouse History)
    // ================================================================

    private VBox buildDetailSection() {
        Label sectionLabel = new Label("Shipment Details");
        sectionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(buildInspectionTab(), buildLogTab());

        VBox vbox = new VBox(6, sectionLabel, tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);
        vbox.setPadding(new Insets(10));
        return vbox;
    }

    // ---- Inspection tab ----

    @SuppressWarnings("unchecked")
    private Tab buildInspectionTab() {
        inspectionTable = new TableView<>();
        inspectionTable.setItems(inspectionList);

        TableColumn<Inspection, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate().toString()));
        dateCol.setMinWidth(95);

        TableColumn<Inspection, String> inspectorCol = new TableColumn<>("Inspector");
        inspectorCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getInspectorName()));
        inspectorCol.setMinWidth(120);

        TableColumn<Inspection, String> resultCol = new TableColumn<>("Result");
        resultCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getResult()));
        resultCol.setMinWidth(150);

        inspectionTable.getColumns().addAll(dateCol, inspectorCol, resultCol);
        inspectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button removeBtn = new Button("Remove");
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        removeBtn.setDisable(true);

        shipmentTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->
                addBtn.setDisable(n == null));
        inspectionTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            editBtn.setDisable(n == null);
            removeBtn.setDisable(n == null);
        });

        addBtn.setOnAction(e -> handleAddInspection());
        editBtn.setOnAction(e -> handleEditInspection());
        removeBtn.setOnAction(e -> handleRemoveInspection());

        HBox buttons = new HBox(5, addBtn, editBtn, removeBtn);
        buttons.setPadding(new Insets(5, 0, 0, 0));

        VBox content = new VBox(5, inspectionTable, buttons);
        VBox.setVgrow(inspectionTable, Priority.ALWAYS);
        content.setPadding(new Insets(5));

        return new Tab("Inspections", content);
    }

    private void handleAddInspection() {
        if (selectedShipment == null) return;
        showInspectionDialog(null);
    }

    private void handleEditInspection() {
        Inspection selected = inspectionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInspectionDialog(selected);
        }
    }

    private void handleRemoveInspection() {
        Inspection selected = inspectionTable.getSelectionModel().getSelectedItem();
        if (selected == null || selectedShipment == null) return;
        inspectionController.removeInspection(selectedShipment, selected);
        inspectionList.remove(selected);
        shipmentTable.refresh();
        warehouseTable.refresh();
    }

    private void showInspectionDialog(Inspection existing) {
        boolean isEdit = existing != null;
        Dialog<ButtonType> dialog = createDialog(isEdit ? "Edit Inspection" : "Add Inspection");

        GridPane grid = createFormGrid();
        DatePicker datePicker = new DatePicker(isEdit ? existing.getDate() : LocalDate.now());
        TextField inspectorField = new TextField(isEdit ? existing.getInspectorName() : "");
        TextField resultField = new TextField(isEdit ? existing.getResult() : "");

        setFormWidth(200, datePicker, inspectorField, resultField);
        addFormRow(grid, 0, "Date:", datePicker);
        addFormRow(grid, 1, "Inspector:", inspectorField);
        addFormRow(grid, 2, "Result:", resultField);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        LocalDate date = datePicker.getValue();
        String inspector = inspectorField.getText().trim();
        String outcome = resultField.getText().trim();

        try {
            if (isEdit) {
                inspectionController.updateInspection(existing, date, inspector, outcome);
                inspectionTable.refresh();
                warehouseTable.refresh();
            } else {
                Inspection newInsp = inspectionController.addInspection(
                        selectedShipment, date, inspector, outcome);
                inspectionList.add(newInsp);
                shipmentTable.refresh();
                warehouseTable.refresh();
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // ---- Warehouse history (log) tab ----

    @SuppressWarnings("unchecked")
    private Tab buildLogTab() {
        logTable = new TableView<>();
        logTable.setItems(logList);

        TableColumn<ShipmentLog, String> warehouseCol = new TableColumn<>("Warehouse");
        warehouseCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getWarehouseName()));
        warehouseCol.setMinWidth(150);

        TableColumn<ShipmentLog, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate().toString()));
        dateCol.setMinWidth(95);

        logTable.getColumns().addAll(warehouseCol, dateCol);
        logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit");
        Button removeBtn = new Button("Remove");
        addBtn.setDisable(true);
        editBtn.setDisable(true);
        removeBtn.setDisable(true);

        shipmentTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) ->
                addBtn.setDisable(n == null));
        logTable.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            editBtn.setDisable(n == null);
            removeBtn.setDisable(n == null);
        });

        addBtn.setOnAction(e -> handleAddLog());
        editBtn.setOnAction(e -> handleEditLog());
        removeBtn.setOnAction(e -> handleRemoveLog());

        HBox buttons = new HBox(5, addBtn, editBtn, removeBtn);
        buttons.setPadding(new Insets(5, 0, 0, 0));

        VBox content = new VBox(5, logTable, buttons);
        VBox.setVgrow(logTable, Priority.ALWAYS);
        content.setPadding(new Insets(5));

        return new Tab("Warehouse History", content);
    }

    private void handleAddLog() {
        if (selectedShipment == null) return;
        showLogDialog(null);
    }

    private void handleEditLog() {
        ShipmentLog selected = logTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showLogDialog(selected);
        }
    }

    private void handleRemoveLog() {
        ShipmentLog selected = logTable.getSelectionModel().getSelectedItem();
        if (selected == null || selectedShipment == null) return;
        logController.removeLog(selectedShipment, selected);
        logList.remove(selected);
    }

    private void showLogDialog(ShipmentLog existing) {
        boolean isEdit = existing != null;
        Dialog<ButtonType> dialog = createDialog(isEdit ? "Edit Log Entry" : "Add Log Entry");

        GridPane grid = createFormGrid();
        TextField warehouseField = new TextField(isEdit ? existing.getWarehouseName() : "");
        DatePicker datePicker = new DatePicker(isEdit ? existing.getDate() : LocalDate.now());

        setFormWidth(200, warehouseField, datePicker);
        addFormRow(grid, 0, "Warehouse:", warehouseField);
        addFormRow(grid, 1, "Date:", datePicker);

        dialog.getDialogPane().setContent(grid);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        String warehouseName = warehouseField.getText().trim();
        LocalDate date = datePicker.getValue();

        try {
            if (isEdit) {
                logController.updateLog(existing, warehouseName, date);
                logTable.refresh();
            } else {
                ShipmentLog newLog = logController.addLog(selectedShipment, warehouseName, date);
                logList.add(newLog);
            }
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    // ================================================================
    //  UTILITIES
    // ================================================================

    private Dialog<ButtonType> createDialog(String title) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        return dialog;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(12));
        return grid;
    }

    private void addFormRow(GridPane grid, int row, String labelText, Node field) {
        grid.add(new Label(labelText), 0, row);
        grid.add(field, 1, row);
    }

    private void setFormWidth(double width, Node... nodes) {
        for (Node node : nodes) {
            if (node instanceof Region region) {
                region.setPrefWidth(width);
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
