package se.lu.ics.vikingexpress.controller;

import se.lu.ics.vikingexpress.model.Inspection;
import se.lu.ics.vikingexpress.model.Shipment;

import java.time.LocalDate;

public class InspectionController {

    /**
     * Creates an inspection record and attaches it to the given shipment.
     *
     * @param shipment      the shipment being inspected
     * @param date          inspection date
     * @param inspectorName name of the inspector
     * @param result        outcome of the inspection
     * @return the created Inspection
     */
    public Inspection addInspection(Shipment shipment, LocalDate date,
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
        Inspection inspection = new Inspection(date, inspectorName, result);
        shipment.addInspection(inspection);
        return inspection;
    }

    /**
     * Updates an existing inspection.
     *
     * @param inspection    the inspection to update
     * @param date          new date
     * @param inspectorName new inspector name
     * @param result        new result
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
        inspection.setDate(date);
        inspection.setInspectorName(inspectorName);
        inspection.setResult(result);
    }

    public void removeInspection(Shipment shipment, Inspection inspection) {
        shipment.removeInspection(inspection);
    }
}
