/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;


import com.example.Delivery.Distrito.Distrito;
import com.example.Delivery.Pedido.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
/**
 *
 * @author INTEL
 */
@Data //Genera los metodos get y set
@Entity
@Table(name = "CLIENTE")
public class Cliente extends Persona{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cliente;
    
    private String usuario;
    private String clave;
    
    public Cliente(){
    }
    
    public Cliente(String nombres, String apellidos, String dni,String correo, String telefono, Distrito distrito, String direccion, String usuario, String clave) {
        super(nombres, apellidos, dni, correo, telefono, direccion);
        this.distrito = distrito;
        this.usuario = usuario;
        this.clave = clave;
    }
    
    @ManyToOne
    @JoinColumn(name = "id_distrito",referencedColumnName = "id_distrito")
    private Distrito distrito;
    
    @OneToMany(mappedBy = "cliente",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Pedido> Lpedido = new ArrayList<Pedido>();
    
    public void addPedido(Pedido pedido) {
        pedido.setCliente(this);
        this.Lpedido.add(pedido);
    }
}
