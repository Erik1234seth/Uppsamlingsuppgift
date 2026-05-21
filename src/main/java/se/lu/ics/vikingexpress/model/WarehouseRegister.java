package se.lu.ics.vikingexpress.model;

import java.util.ArrayList;

public class WarehouseRegister {

    private ArrayList<Warehouse> warehouses;

    public WarehouseRegister() {
        this.warehouses = new ArrayList<>();
    }

    public ArrayList<Warehouse> getWarehouses() {
        return warehouses;
    }

    public boolean addWarehouse(Warehouse warehouse) {
        if (warehouseNameExists(warehouse.getName())) {
            return false;
        }
        warehouses.add(warehouse);
        return true;
    }

    public void removeWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }

    public boolean warehouseNameExists(String name) {
        return warehouses.stream()
                .anyMatch(w -> w.getName().equalsIgnoreCase(name));
    }
}
