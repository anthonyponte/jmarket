/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package pe.gob.sunat.jmarket.dao;

import pe.gob.sunat.jmarket.model.Persona;

/**
 * @author anthonyponte
 */
public interface PersonaDao extends ObjectDao<Persona> {
  Persona read(String numeroDocumento);
}
