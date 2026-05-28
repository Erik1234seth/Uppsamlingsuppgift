package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Shipment;
import se.lu.ics.vikingexpress.model.ShipmentLog;

import java.time.LocalDate;

/**
 * KLASS: ShipmentLogController (Controller i MVC)
 * Ansvarar för all validering och alla ändringar som rör lager-logg-inlägg.
 * Logg-inlägg tillhör alltid en försändelse, så alla metoder kräver att försändelsen skickas in.
 */
public class ShipmentLogController {

    /**
     * METOD: addLog
     * Skapar ett nytt logg-inlägg och kopplar det till den angivna försändelsen.
     *
     * PARAMETRAR:
     *   shipment      — försändelsen som besökte lagret
     *   warehouseName — namnet på lagret (får inte vara tomt)
     *   date          — datumet för besöket (får inte vara null)
     * Returtyp: ShipmentLog (det nyskapade objektet returneras till vyn)
     */
    public ShipmentLog addLog(Shipment shipment, String warehouseName, LocalDate date) {
        // Validera inmatningarna innan objektet skapas
        if (warehouseName == null || warehouseName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Log date is required.");
        }
        // Skapa ett nytt ShipmentLog-objekt och koppla det till försändelsen
        ShipmentLog log = new ShipmentLog(warehouseName, date);
        shipment.addLog(log);
        return log;
    }

    /**
     * METOD: updateLog
     * Uppdaterar ett befintligt logg-inläggets lagernamn och datum.
     *
     * PARAMETRAR:
     *   log           — det logg-inlägg som ska uppdateras
     *   warehouseName — nytt lagernamn
     *   date          — nytt datum
     * Returtyp: void
     */
    public void updateLog(ShipmentLog log, String warehouseName, LocalDate date) {
        if (warehouseName == null || warehouseName.isBlank()) {
            throw new IllegalArgumentException("Warehouse name cannot be empty.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Log date is required.");
        }
        // Anropa setters på ShipmentLog-objektet för att spara de nya värdena
        log.setWarehouseName(warehouseName);
        log.setDate(date);
    }

    /**
     * METOD: removeLog
     * Tar bort ett logg-inlägg från försändelsens lista.
     *
     * PARAMETRAR:
     *   shipment — försändelsen som logg-inlägget tillhör
     *   log      — logg-inlägget som ska tas bort
     * Returtyp: void
     */
    public void removeLog(Shipment shipment, ShipmentLog log) {
        shipment.removeLog(log);
    }
}
