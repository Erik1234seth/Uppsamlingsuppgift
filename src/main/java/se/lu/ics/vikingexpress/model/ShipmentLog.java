package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;

public class ShipmentLog {

    private String warehouseName;
    private LocalDate date;

    public ShipmentLog(String warehouseName, LocalDate date) {
        this.warehouseName = warehouseName;
        this.date = date;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
