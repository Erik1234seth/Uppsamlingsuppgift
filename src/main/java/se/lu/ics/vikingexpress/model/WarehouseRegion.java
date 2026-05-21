package se.lu.ics.vikingexpress.model;

public enum WarehouseRegion {
    NORTH, MIDDLE, SOUTH;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
