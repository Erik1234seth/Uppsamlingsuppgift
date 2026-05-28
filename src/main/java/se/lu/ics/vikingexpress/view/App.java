package se.lu.ics.vikingexpress.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.lu.ics.vikingexpress.controller.InspectionController;
import se.lu.ics.vikingexpress.controller.ShipmentController;
import se.lu.ics.vikingexpress.controller.ShipmentLogController;
import se.lu.ics.vikingexpress.controller.WarehouseController;
import se.lu.ics.vikingexpress.model.Shipment;
import se.lu.ics.vikingexpress.model.Warehouse;
import se.lu.ics.vikingexpress.model.WarehouseRegion;

import java.time.LocalDate;

/**
 * Entry point for the VikingExpress application.
 * Creates all controllers, loads sample data, and launches the main window.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create one controller for each type of data in the system
        WarehouseController warehouseController = new WarehouseController();
        ShipmentController shipmentController = new ShipmentController();
        InspectionController inspectionController = new InspectionController();
        ShipmentLogController logController = new ShipmentLogController();

        // Populate the system with sample data so the application is ready to present
        loadSampleData(warehouseController, shipmentController, inspectionController, logController);

        // Build the main view and pass all controllers into it
        MainView mainView = new MainView(warehouseController, shipmentController,
                inspectionController, logController);

        // Set up and show the window
        Scene scene = new Scene(mainView, 1200, 700);
        primaryStage.setTitle("VikingExpress - Inventory Management");
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Populates the system with three warehouses, seven shipments, six inspections,
     * and nine log entries to satisfy the presentation requirement of test data for
     * every class and every association.
     */
    private void loadSampleData(WarehouseController warehouseController,
                                ShipmentController shipmentController,
                                InspectionController inspectionController,
                                ShipmentLogController logController) {
        // Create three warehouses, one per region
        Warehouse odinsVault = warehouseController.addWarehouse(
                "Odin's Vault", "Norrlandsgatan 12, Sundsvall", WarehouseRegion.NORTH, 100);
        Warehouse valhallaHub = warehouseController.addWarehouse(
                "Valhalla Hub", "Storgatan 5, Stockholm", WarehouseRegion.MIDDLE, 200);
        Warehouse midgardStore = warehouseController.addWarehouse(
                "Midgard Store", "Södra Vägen 8, Malmö", WarehouseRegion.SOUTH, 150);

        // Odin's Vault shipments
        Shipment s1 = shipmentController.addShipment(odinsVault, "Industrial machinery parts", true);
        Shipment s2 = shipmentController.addShipment(odinsVault, "Electronic components", false);

        inspectionController.addInspection(s1, LocalDate.of(2025, 3, 10), "Anna Lindqvist", "Approved");
        inspectionController.addInspection(s2, LocalDate.of(2025, 4, 5), "Erik Svensson", "Minor issues - resolved");

        logController.addLog(s1, "Odin's Vault", LocalDate.of(2025, 3, 8));
        logController.addLog(s2, "Valhalla Hub", LocalDate.of(2025, 3, 20));
        logController.addLog(s2, "Odin's Vault", LocalDate.of(2025, 4, 1));

        // Valhalla Hub shipments
        Shipment s3 = shipmentController.addShipment(valhallaHub, "Textile goods", true);
        Shipment s4 = shipmentController.addShipment(valhallaHub, "Frozen food products", true);
        Shipment s5 = shipmentController.addShipment(valhallaHub, "Consumer electronics", false);

        inspectionController.addInspection(s3, LocalDate.of(2025, 5, 2), "Maria Johansson", "Approved");
        inspectionController.addInspection(s4, LocalDate.of(2025, 5, 10), "Anna Lindqvist", "Failed - temperature issue");

        logController.addLog(s3, "Valhalla Hub", LocalDate.of(2025, 4, 28));
        logController.addLog(s4, "Midgard Store", LocalDate.of(2025, 4, 15));
        logController.addLog(s4, "Valhalla Hub", LocalDate.of(2025, 5, 1));
        logController.addLog(s5, "Valhalla Hub", LocalDate.of(2025, 5, 8));

        // Midgard Store shipments
        Shipment s6 = shipmentController.addShipment(midgardStore, "Construction materials", true);
        Shipment s7 = shipmentController.addShipment(midgardStore, "Pharmaceutical supplies", false);

        inspectionController.addInspection(s6, LocalDate.of(2025, 2, 18), "Johan Berg", "Approved");
        inspectionController.addInspection(s7, LocalDate.of(2025, 3, 22), "Maria Johansson", "Approved");

        logController.addLog(s6, "Midgard Store", LocalDate.of(2025, 2, 15));
        logController.addLog(s7, "Odin's Vault", LocalDate.of(2025, 3, 1));
        logController.addLog(s7, "Midgard Store", LocalDate.of(2025, 3, 18));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
