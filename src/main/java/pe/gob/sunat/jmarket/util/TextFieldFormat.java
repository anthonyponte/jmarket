/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * @author anthonyponte
 */
public class TextFieldFormat {
  public static void toUpperCase(TextField textField) {
    textField.setTextFormatter(
        new TextFormatter<>(
            (change) -> {
              change.setText(change.getText().toUpperCase());
              return change;
            }));
  }
}
