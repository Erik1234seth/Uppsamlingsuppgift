package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Warehouse;
import se.lu.ics.vikingexpress.model.WarehouseRegion;

import java.util.ArrayList;

/**
 * KLASS: WarehouseController (Controller i MVC)
 * Ansvarar för all validering och alla ändringar som rör lager.
 * Vyn (MainView) anropar metoderna här istället för att skapa Warehouse-objekt direkt —
 * det garanterar att all data kontrolleras innan den sparas.
 */
public class WarehouseController {

    // FÄLT: en lista med alla lager i systemet.
    // ArrayList är en dynamisk lista som kan växa och krympa.
    private ArrayList<Warehouse> warehouses;

    /**
     * KONSTRUKTOR: körs när man skriver "new WarehouseController()".
     * Tar inga PARAMETRAR — skapar bara en tom lista att fylla på.
     */
    public WarehouseController() {
        this.warehouses = new ArrayList<>();
    }

    /**
     * METOD: warehouseNameExists (privat hjälpmetod)
     * Kontrollerar om ett lager med det givna namnet redan finns.
     * Jämförelsen är skiftlägesokänslig så "Odin's Vault" och "odin's vault" räknas som samma.
     *
     * PARAMETER:
     *   name — lagernamnet som ska sökas efter
     * Returtyp: boolean (true om namnet redan finns)
     *
     * "private" betyder att den bara används internt i den här klassen.
     */
    private boolean warehouseNameExists(String name) {
        for (Warehouse w : warehouses) {
            if (w.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * METOD: addWarehouse
     * Skapar ett nytt lager och lägger till det i listan.
     * Kastar IllegalArgumentException om något värde är ogiltigt.
     *
     * PARAMETRAR:
     *   name     — lagerets namn (måste vara unikt och inte tomt)
     *   address  — gatuadress
     *   region   — NORTH, MIDDLE eller SOUTH
     *   capacity — max antal försändelser
     * Returtyp: Warehouse (det nyskapade objektet returneras till vyn)
     */
    public Warehouse addWarehouse(String name, String address, WarehouseRegion region, int capacity) {
        // Validera alla inmatningar innan något objekt skapas
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        if (warehouseNameExists(name)) {
            throw new IllegalArgumentException("A warehouse named \"" + name + "\" already exists.");
        }
        // Skapa Warehouse-objektet och lägg till det i listan
        Warehouse warehouse = new Warehouse(name, address, region, capacity);
        warehouses.add(warehouse);
        return warehouse;
    }

    /**
     * METOD: updateWarehouse
     * Uppdaterar ett befintligt lagers information.
     * Tillåter att man behåller samma namn, men blockerar om det nya namnet tillhör ett annat lager.
     *
     * PARAMETRAR:
     *   warehouse — det lager-objekt som ska uppdateras
     *   newName   — nytt namn
     *   address   — ny adress
     *   region    — ny region
     *   capacity  — ny kapacitet
     * Returtyp: void (ingenting returneras)
     */
    public void updateWarehouse(Warehouse warehouse, String newName, String address,
                                WarehouseRegion region, int capacity) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        // Tillåt samma namn som förut, men inte ett namn som ett ANNAT lager redan har
        if (!warehouse.getName().equalsIgnoreCase(newName) && warehouseNameExists(newName)) {
            throw new IllegalArgumentException("A warehouse named \"" + newName + "\" already exists.");
        }
        // Anropa setters på Warehouse-objektet för att spara de nya värdena
        warehouse.setName(newName);
        warehouse.setAddress(address);
        warehouse.setRegion(region);
        warehouse.setCapacity(capacity);
    }

    /**
     * METOD: removeWarehouse
     * Tar bort ett lager från listan.
     *
     * PARAMETER:
     *   warehouse — det lager-objekt som ska tas bort
     * Returtyp: void
     */
    public void removeWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }

    /**
     * METOD: getAllWarehouses
     * Returnerar hela listan med lager så att vyn kan visa dem.
     * Ingen PARAMETER.
     * Returtyp: ArrayList<Warehouse>
     */
    public ArrayList<Warehouse> getAllWarehouses() {
        return warehouses;
    }
}
