/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;

import javax.persistence.MappedSuperclass;
import lombok.Data;
/**
 *
 * @author INTEL
 */
@Data
@MappedSuperclass
public class Persona {
    private String nombres;
    private String apellidos;
    private String dni;
    private String correo;
    private String telefono;
    private String direccion;

    public Persona() {
    }

    public Persona(String nombres, String apellidos, String dni, String correo, String telefono, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
    }
    
}
