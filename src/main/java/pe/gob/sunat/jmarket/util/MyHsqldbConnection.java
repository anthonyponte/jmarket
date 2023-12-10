/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pe.gob.sunat.jmarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author anthony
 */
public class MyHsqldbConnection {
  private final String ALIAS = "jmarket";
  private final String USER = "SA";
  private final String PASS = "";
  private final String URL = "jdbc:hsqldb:hsql://localhost/" + ALIAS;
  private Connection connection = null;

  public void connect() {
    try {
      connection = DriverManager.getConnection(URL, USER, PASS);
    } catch (SQLException ex) {
      Logger.getLogger(MyHsqldbConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void disconnect() {
    try {
      connection.close();
    } catch (SQLException ex) {
      Logger.getLogger(MyHsqldbConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public Connection getConnection() {
    return connection;
  }
}
