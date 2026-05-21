package se.lu.ics.vikingexpress.model;

import java.time.LocalDate;

public class Inspection {

    private LocalDate date;
    private String inspectorName;
    private String result;

    public Inspection(LocalDate date, String inspectorName, String result) {
        this.date = date;
        this.inspectorName = inspectorName;
        this.result = result;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
