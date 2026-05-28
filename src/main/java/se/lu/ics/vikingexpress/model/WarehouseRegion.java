package se.lu.ics.vikingexpress.model;

/**
 * ENUM: WarehouseRegion
 * En enum är en speciell klass med ett fast antal fördefinierade värden.
 * Istället för att skriva regionen som en vanlig String (t.ex. "North") använder
 * vi en enum — då kan man inte råka stava fel och alla giltiga värden syns tydligt.
 *
 * De tre möjliga värdena är: NORTH, MIDDLE, SOUTH.
 */
public enum WarehouseRegion {
    NORTH, MIDDLE, SOUTH;

    /**
     * METOD: toString() — en metod som alla Java-objekt har.
     * Här skriver vi om den (override) så att "NORTH" visas som "North" i gränssnittet.
     * Ingen PARAMETER — den arbetar bara med det egna värdet (name()).
     * Returnerar en String.
     */
    @Override
    public String toString() {
        // name() returnerar enum-värdet som text, t.ex. "NORTH"
        // charAt(0) tar första bokstaven — "N"
        // substring(1).toLowerCase() tar resten och gör den liten — "orth"
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
