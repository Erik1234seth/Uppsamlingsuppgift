package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Shipment;
import se.lu.ics.vikingexpress.model.ShipmentLog;

import java.time.LocalDate;

public class ShipmentLogController {

    /**
     * Creates a warehouse location log entry for the given shipment.
     *
     * @param shipment      the shipment to log
     * @param warehouseName name of the warehouse
     * @param date          date the shipment arrived at that warehouse
     * @return the created ShipmentLog
     */
    public ShipmentLog addLog(Shipment shipment, String warehouseName, LocalDate date) {
        if (warehouseName == null || warehouseName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Log date is required.");
        }
        ShipmentLog log = new ShipmentLog(warehouseName, date);
        shipment.addLog(log);
        return log;
    }

    /**
     * Updates an existing log entry.
     *
     * @param log           the log to update
     * @param warehouseName new warehouse name
     * @param date          new date
     */
    public void updateLog(ShipmentLog log, String warehouseName, LocalDate date) {
        if (warehouseName == null || warehouseName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Log date is required.");
        }
        log.setWarehouseName(warehouseName);
        log.setDate(date);
    }

    public void removeLog(Shipment shipment, ShipmentLog log) {
        shipment.removeLog(log);
    }
}
