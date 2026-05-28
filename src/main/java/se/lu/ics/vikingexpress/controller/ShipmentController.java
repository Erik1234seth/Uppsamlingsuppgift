package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Shipment;
import se.lu.ics.vikingexpress.model.Warehouse;

/**
 * KLASS: ShipmentController (Controller i MVC)
 * Ansvarar för all validering och alla ändringar som rör försändelser.
 * Försändelser tillhör alltid ett lager, så alla metoder kräver att lagret skickas in.
 */
public class ShipmentController {

    /**
     * METOD: addShipment
     * Skapar en ny försändelse och lägger till den på det angivna lagret.
     *
     * PARAMETRAR:
     *   warehouse   — lagret som försändelsen tillhör
     *   description — vad försändelsen innehåller (får inte vara tomt)
     *   isIncoming  — true om den anländer, false om den ska ut
     * Returtyp: Shipment (det nyskapade objektet returneras till vyn)
     */
    public Shipment addShipment(Warehouse warehouse, String description, boolean isIncoming) {
        // Validera beskrivningen innan objektet skapas
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Shipment description cannot be empty.");
        }
        // Skapa ett nytt Shipment-objekt och koppla det till lagret
        Shipment shipment = new Shipment(description, isIncoming);
        warehouse.addShipment(shipment);
        return shipment;
    }

    /**
     * METOD: updateShipment
     * Uppdaterar en befintlig försändelses beskrivning och riktning.
     *
     * PARAMETRAR:
     *   shipment    — det försändelse-objekt som ska uppdateras
     *   description — ny beskrivning
     *   isIncoming  — ny riktning
     * Returtyp: void
     */
    public void updateShipment(Shipment shipment, String description, boolean isIncoming) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Shipment description cannot be empty.");
        }
        // Anropa setters på Shipment-objektet för att spara de nya värdena
        shipment.setDescription(description);
        shipment.setIncoming(isIncoming);
    }

    /**
     * METOD: removeShipment
     * Tar bort en försändelse från lagrets lista.
     *
     * PARAMETRAR:
     *   warehouse — lagret försändelsen tillhör
     *   shipment  — försändelsen som ska tas bort
     * Returtyp: void
     */
    public void removeShipment(Warehouse warehouse, Shipment shipment) {
        warehouse.removeShipment(shipment);
    }
}
