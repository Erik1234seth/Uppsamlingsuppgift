package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Warehouse {

    private String name;
    private String address;
    private WarehouseRegion region;
    private int capacity;
    private ArrayList<Shipment> shipments;

    public Warehouse(String name, String address, WarehouseRegion region, int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.name = name;
        this.address = address;
        this.region = region;
        this.capacity = capacity;
        this.shipments = new ArrayList<>();
    }

    /**
     * Returns the date of the most recent inspection among all shipments in this warehouse,
     * or null if no inspections exist.
     */
    public LocalDate getLastInspectionDate() {
        return shipments.stream()
                .flatMap(s -> s.getInspections().stream())
                .map(Inspection::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    public int getStockLevel() {
        return shipments.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public WarehouseRegion getRegion() {
        return region;
    }

    public void setRegion(WarehouseRegion region) {
        this.region = region;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    public void removeShipment(Shipment shipment) {
        shipments.remove(shipment);
    }
}
