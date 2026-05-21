package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Warehouse;
import se.lu.ics.vikingexpress.model.WarehouseRegion;
import se.lu.ics.vikingexpress.model.WarehouseRegister;

import java.util.ArrayList;

public class WarehouseController {

    private WarehouseRegister register;

    public WarehouseController(WarehouseRegister register) {
        this.register = register;
    }

    /**
     * Creates and adds a new warehouse. Returns the new warehouse, or throws if invalid.
     *
     * @param name     unique warehouse name
     * @param address  street address
     * @param region   geographical region
     * @param capacity maximum number of shipments
     * @return the created Warehouse
     */
    public Warehouse addWarehouse(String name, String address, WarehouseRegion region, int capacity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        if (register.warehouseNameExists(name)) {
            throw new IllegalArgumentException("A warehouse named \"" + name + "\" already exists.");
        }
        Warehouse warehouse = new Warehouse(name, address, region, capacity);
        register.addWarehouse(warehouse);
        return warehouse;
    }

    /**
     * Updates an existing warehouse's information.
     *
     * @param warehouse the warehouse to update
     * @param newName   new unique name
     * @param address   new address
     * @param region    new region
     * @param capacity  new capacity (must be >= current stock)
     */
    public void updateWarehouse(Warehouse warehouse, String newName, String address,
                                WarehouseRegion region, int capacity) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        if (!warehouse.getName().equalsIgnoreCase(newName) && register.warehouseNameExists(newName)) {
            throw new IllegalArgumentException("A warehouse named \"" + newName + "\" already exists.");
        }
        warehouse.setName(newName);
        warehouse.setAddress(address);
        warehouse.setRegion(region);
        warehouse.setCapacity(capacity);
    }

    public void removeWarehouse(Warehouse warehouse) {
        register.removeWarehouse(warehouse);
    }

    public ArrayList<Warehouse> getAllWarehouses() {
        return register.getWarehouses();
    }
}
