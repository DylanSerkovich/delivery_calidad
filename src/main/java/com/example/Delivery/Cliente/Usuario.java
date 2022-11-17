/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;

import javax.persistence.Embeddable;
import lombok.Data;
/**
 *
 * @author INTEL
 */
@Data
@Embeddable //Permite integrarse a otras clases
public class Usuario {
    private String usuario;
    private String clave;

    public Usuario() {
    }

    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    
}
