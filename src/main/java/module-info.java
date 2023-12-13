module pe.gob.sunat.jmarket {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.base;
  requires java.sql;
  requires org.kordamp.ikonli.remixicon;
  requires org.kordamp.ikonli.core;
  requires org.kordamp.ikonli.javafx;

  opens pe.gob.sunat.jmarket.controller to
      javafx.fxml;

  exports pe.gob.sunat.jmarket;
}
