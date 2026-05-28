package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * KLASS: Warehouse
 * Representerar ett lager i VikingExpress-systemet.
 * Varje lager har ett unikt namn, en adress, en region och en maxkapacitet.
 * Det håller också en lista med alla försändelser som finns på lagret.
 */
public class Warehouse {

    // FÄLT (instansvariabler): varje Warehouse-objekt har sina egna kopior av dessa.
    // "private" innebär att de inte kan ändras direkt utifrån — man måste gå via metoder.
    private String name;
    private String address;
    private WarehouseRegion region;
    private int capacity;
    private ArrayList<Shipment> shipments;  // lista med försändelser på detta lager

    /**
     * KONSTRUKTOR: körs när man skriver "new Warehouse(name, address, region, capacity)".
     * Kontrollerar att kapaciteten inte är negativ och skapar sedan objektet.
     *
     * PARAMETRAR:
     *   name     — lagerets unika namn
     *   address  — gatuadress
     *   region   — NORTH, MIDDLE eller SOUTH
     *   capacity — max antal försändelser lagret kan hålla
     */
    public Warehouse(String name, String address, WarehouseRegion region, int capacity) {
        // Kapacitet måste vara noll eller positivt — detta kontrolleras redan i konstruktorn
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.name = name;
        this.address = address;
        this.region = region;
        this.capacity = capacity;
        // Startar med en tom lista — försändelser läggs till via addShipment()
        this.shipments = new ArrayList<>();
    }

    /**
     * METOD: getLastInspectionDate
     * Returnerar datumet för den senaste inspektionen bland alla försändelser på lagret.
     * Returnerar null om inga inspektioner finns.
     * Ingen PARAMETER — arbetar med fältet shipments.
     * Returtyp: LocalDate (eller null)
     *
     * Går igenom varje försändelse, sedan varje inspektion på varje försändelse,
     * samlar alla datum och returnerar det senaste.
     */
    public LocalDate getLastInspectionDate() {
        return shipments.stream()
                .flatMap(s -> s.getInspections().stream())
                .map(Inspection::getDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    /**
     * METOD: getStockLevel
     * Returnerar hur många försändelser som just nu finns på lagret.
     * Ingen PARAMETER — räknar bara antalet objekt i listan shipments.
     * Returtyp: int
     */
    public int getStockLevel() {
        return shipments.size();
    }

    // GETTER-METODER: returnerar värdet på ett fält.

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public WarehouseRegion getRegion() {
        return region;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Shipment> getShipments() {
        return shipments;
    }

    // SETTER-METODER: tar en PARAMETER med nytt värde och skriver över det gamla.
    // setCapacity kontrollerar att det nya värdet inte är negativt.

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRegion(WarehouseRegion region) {
        this.region = region;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    // METODER FÖR ATT HANTERA LISTAN: lägger till eller tar bort en försändelse.

    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    public void removeShipment(Shipment shipment) {
        shipments.remove(shipment);
    }
}
