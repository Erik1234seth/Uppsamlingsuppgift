package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;

/**
 * KLASS: ShipmentLog
 * Representerar ett logg-inlägg som visar att en försändelse befann sig
 * på ett visst lager ett visst datum.
 * Används för att spåra försändelsens resa mellan lager.
 */
public class ShipmentLog {

    // FÄLT (instansvariabler): varje ShipmentLog-objekt lagrar dessa två värden.
    // "private" innebär att de bara kan läsas/ändras via getters och setters.
    private String warehouseName;
    private LocalDate date;

    /**
     * KONSTRUKTOR: körs när man skriver "new ShipmentLog(warehouseName, date)".
     * Tar emot två PARAMETRAR och sparar dem i fälten.
     *
     * PARAMETRAR:
     *   warehouseName — namnet på lagret försändelsen var på
     *   date          — datumet den anlände dit
     */
    public ShipmentLog(String warehouseName, LocalDate date) {
        this.warehouseName = warehouseName;
        this.date = date;
    }

    // GETTER-METODER: returnerar värdet på ett fält så att andra klasser kan läsa det.

    public String getWarehouseName() {
        return warehouseName;
    }

    public LocalDate getDate() {
        return date;
    }

    // SETTER-METODER: tar en PARAMETER med nytt värde och skriver över det gamla.

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
