module se.lu.ics.vikingexpress {
    requires javafx.controls;
    opens se.lu.ics.vikingexpress.view to javafx.graphics;
    exports se.lu.ics.vikingexpress.view;
}
