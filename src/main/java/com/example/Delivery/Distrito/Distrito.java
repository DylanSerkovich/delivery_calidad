/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Distrito;

import com.example.Delivery.Cliente.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author INTEL
 */
@Data
@Entity
@Table(name = "DISTRITO")
public class Distrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_distrito;
    private String nombre_distrito;
    private String tiempo_envio;

    public Distrito() {
    }

    public Distrito(int id_distrito, String nombre_distrito, String tiempo_envio) {
        this.id_distrito = id_distrito;
        this.nombre_distrito = nombre_distrito;
        this.tiempo_envio = tiempo_envio;
    }
    
    @OneToMany(mappedBy = "distrito", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Cliente> clientes = new ArrayList<Cliente>();
}
