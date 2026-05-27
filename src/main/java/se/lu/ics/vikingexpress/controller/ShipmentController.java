package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Shipment;
import se.lu.ics.vikingexpress.model.Warehouse;

public class ShipmentController {

    /**
     * Creates a new shipment and adds it to the given warehouse.
     *
     * @param warehouse   the warehouse to add the shipment to
     * @param description description of the shipment
     * @param isIncoming  true if incoming, false if outgoing
     * @return the created Shipment
     */
    public Shipment addShipment(Warehouse warehouse, String description, boolean isIncoming) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Shipment description cannot be empty.");
        }
        Shipment shipment = new Shipment(description, isIncoming);
        warehouse.addShipment(shipment);
        return shipment;
    }

    /**
     * Updates an existing shipment's mutable fields.
     *
     * @param shipment    the shipment to update
     * @param description new description
     * @param isIncoming  new direction flag
     */
    public void updateShipment(Shipment shipment, String description, boolean isIncoming) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Shipment description cannot be empty.");
        }
        shipment.setDescription(description);
        shipment.setIncoming(isIncoming);
    }

    public void removeShipment(Warehouse warehouse, Shipment shipment) {
        warehouse.removeShipment(shipment);
    }
}
