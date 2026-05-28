package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;

/**
 * KLASS: Inspection
 * En klass är en mall/ritning som beskriver hur ett objekt ska se ut.
 * Den här klassen beskriver hur en inspektion ser ut i systemet.
 * När man skriver "new Inspection(...)" skapas ett objekt baserat på den här mallen.
 */
public class Inspection {

    // FÄLT (instansvariabler): varje Inspection-objekt har sina egna kopior av dessa värden.
    // De är "private" så att bara klassen själv kan ändra dem direkt — det kallas inkapsling.
    private LocalDate date;
    private String inspectorName;
    private String result;

    /**
     * KONSTRUKTOR: körs automatiskt när man skriver "new Inspection(...)".
     * Den tar emot tre PARAMETRAR (date, inspectorName, result) och sparar dem i fälten.
     * Utan konstruktorn kan man inte skapa ett Inspection-objekt.
     *
     * PARAMETRAR: värdena som skickas in inom parenteserna när objektet skapas.
     *   date          — vilket datum inspektionen gjordes
     *   inspectorName — vem som utförde inspektionen
     *   result        — vad resultatet blev
     */
    public Inspection(LocalDate date, String inspectorName, String result) {
        // "this.X = X" kopplar parametern till fältet som tillhör detta objekt
        this.date = date;
        this.inspectorName = inspectorName;
        this.result = result;
    }

    // GETTER-METODER: returnerar värdet på ett fält.
    // Eftersom fälten är private måste man använda en getter för att läsa dem utifrån.

    public LocalDate getDate() {
        return date;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public String getResult() {
        return result;
    }

    // SETTER-METODER: ändrar värdet på ett fält.
    // Tar en PARAMETER med det nya värdet och tilldelar det till fältet.

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
