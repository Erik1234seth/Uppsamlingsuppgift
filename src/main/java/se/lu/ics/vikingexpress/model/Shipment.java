package se.lu.ics.vikingexpress.model;

import java.util.ArrayList;

/**
 * KLASS: Shipment
 * Representerar en försändelse som är lagrad på ett lager.
 * Varje försändelse har ett unikt ID, en beskrivning, en riktning (in/ut),
 * samt listor med inspektioner och lager-logg-inlägg.
 */
public class Shipment {

    // STATISKT FÄLT: delas av ALLA Shipment-objekt, inte bara ett.
    // Används som en räknare för att ge varje försändelse ett unikt ID (SHP-001, SHP-002, ...).
    // "static" betyder att det finns en enda kopia oavsett hur många objekt som skapas.
    private static int counter = 1;

    // INSTANSFÄLT: varje Shipment-objekt har sina egna kopior av dessa.
    private String id;
    private String description;
    private boolean isIncoming;
    private ArrayList<Inspection> inspections;   // lista med inspektioner kopplade till denna försändelse
    private ArrayList<ShipmentLog> logs;          // lista med lagerbesök för denna försändelse

    /**
     * KONSTRUKTOR: körs när man skriver "new Shipment(description, isIncoming)".
     * Genererar automatiskt ett unikt ID med hjälp av den statiska räknaren.
     *
     * PARAMETRAR:
     *   description — vad försändelsen innehåller, t.ex. "Electronic components"
     *   isIncoming  — true om den anländer till lagret, false om den ska ut
     */
    public Shipment(String description, boolean isIncoming) {
        // Tilldela ett unikt ID och öka räknaren direkt efteråt (counter++ = använd, öka sedan)
        this.id = String.format("SHP-%03d", counter++);
        this.description = description;
        this.isIncoming = isIncoming;
        // Skapa tomma listor — fylls på när inspektioner och logg-inlägg läggs till
        this.inspections = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    /**
     * METOD: isInspected
     * Returnerar true om minst en inspektion har registrerats för denna försändelse.
     * Ingen PARAMETER — arbetar bara med fältet inspections.
     * Returtyp: boolean
     */
    public boolean isInspected() {
        return !inspections.isEmpty();
    }

    // GETTER-METODER: returnerar värdet på ett fält.
    // id har ingen setter — det ska aldrig ändras efter att försändelsen skapades.

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

    public ArrayList<ShipmentLog> getLogs() {
        return logs;
    }

    // SETTER-METODER: tar en PARAMETER med nytt värde och uppdaterar fältet.

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIncoming(boolean incoming) {
        this.isIncoming = incoming;
    }

    // METODER FÖR ATT HANTERA LISTOR:
    // Lägger till eller tar bort objekt från inspektions- och logg-listorna.

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
