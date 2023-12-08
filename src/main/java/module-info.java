module pe.gob.sunat.jmarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens pe.gob.sunat.jmarket.controller to javafx.fxml;
    exports pe.gob.sunat.jmarket;
}
