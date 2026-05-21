package se.lu.ics.vikingexpress.model;

import java.util.ArrayList;

public class Shipment {

    private static int counter = 1;

    private String id;
    private String description;
    private boolean isIncoming;
    private ArrayList<Inspection> inspections;
    private ArrayList<ShipmentLog> logs;

    public Shipment(String description, boolean isIncoming) {
        this.id = String.format("SHP-%03d", counter++);
        this.description = description;
        this.isIncoming = isIncoming;
        this.inspections = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public boolean isInspected() {
        return !inspections.isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public void setIncoming(boolean incoming) {
        this.isIncoming = incoming;
    }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

    public ArrayList<ShipmentLog> getLogs() {
        return logs;
    }

    public void addInspection(Inspection inspection) {
        inspections.add(inspection);
    }

    public void removeInspection(Inspection inspection) {
        inspections.remove(inspection);
    }

    public void addLog(ShipmentLog log) {
        logs.add(log);
    }

    public void removeLog(ShipmentLog log) {
        logs.remove(log);
    }
}
