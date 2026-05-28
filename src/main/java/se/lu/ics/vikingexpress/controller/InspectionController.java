package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Inspection;
import se.lu.ics.vikingexpress.model.Shipment;

import java.time.LocalDate;

/**
 * KLASS: InspectionController (Controller i MVC)
 * Ansvarar för all validering och alla ändringar som rör inspektioner.
 * Inspektioner tillhör alltid en försändelse, så alla metoder kräver att försändelsen skickas in.
 */
public class InspectionController {

    /**
     * METOD: addInspection
     * Skapar en ny inspektion och kopplar den till den angivna försändelsen.
     *
     * PARAMETRAR:
     *   shipment      — försändelsen som inspekterades
     *   date          — datum för inspektionen (får inte vara null)
     *   inspectorName — vem som utförde inspektionen (får inte vara tomt)
     *   result        — resultatet av inspektionen (får inte vara tomt)
     * Returtyp: Inspection (det nyskapade objektet returneras till vyn)
     */
    public Inspection addInspection(Shipment shipment, LocalDate date,
                                    String inspectorName, String result) {
        // Validera alla fält innan objektet skapas
        if (date == null) {
            throw new IllegalArgumentException("Inspection date is required.");
        }
        if (inspectorName == null || inspectorName.isBlank()) {
            throw new IllegalArgumentException("Inspector name cannot be empty.");
        }
        if (result == null || result.isBlank()) {
            throw new IllegalArgumentException("Inspection result cannot be empty.");
        }
        // Skapa ett nytt Inspection-objekt och koppla det till försändelsen
        Inspection inspection = new Inspection(date, inspectorName, result);
        shipment.addInspection(inspection);
        return inspection;
    }

    /**
     * METOD: updateInspection
     * Uppdaterar en befintlig inspektions alla fält.
     *
     * PARAMETRAR:
     *   inspection    — det inspektions-objekt som ska uppdateras
     *   date          — nytt datum
     *   inspectorName — nytt inspektörsnamn
     *   result        — nytt resultat
     * Returtyp: void
     */
    public void updateInspection(Inspection inspection, LocalDate date,
                                 String inspectorName, String result) {
        if (date == null) {
            throw new IllegalArgumentException("Inspection date is required.");
        }
        if (inspectorName == null || inspectorName.isBlank()) {
            throw new IllegalArgumentException("Inspector name cannot be empty.");
        }
        if (result == null || result.isBlank()) {
            throw new IllegalArgumentException("Inspection result cannot be empty.");
        }
        // Anropa setters på Inspection-objektet för att spara de nya värdena
        inspection.setDate(date);
        inspection.setInspectorName(inspectorName);
        inspection.setResult(result);
    }

    /**
     * METOD: removeInspection
     * Tar bort en inspektion från försändelsens lista.
     *
     * PARAMETRAR:
     *   shipment   — försändelsen inspektionen tillhör
     *   inspection — inspektionen som ska tas bort
     * Returtyp: void
     */
    public void removeInspection(Shipment shipment, Inspection inspection) {
        shipment.removeInspection(inspection);
    }
}
