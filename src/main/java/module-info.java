module pe.gob.sunat.jmarket {
    requires javafx.controls;
    requires javafx.fxml;

    opens pe.gob.sunat.jmarket to javafx.fxml;
    exports pe.gob.sunat.jmarket;
}
