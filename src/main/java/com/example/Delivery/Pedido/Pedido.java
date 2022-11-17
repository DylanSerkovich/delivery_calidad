/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Pedido;

import com.example.Delivery.Cliente.Cliente;
import com.example.Delivery.ProductoPedido.ProductoPedido;
import java.sql.Date;
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
@Data
@Entity
@Table(name = "PEDIDO")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_pedido;
    private String nombre_destinatario;
    private String direccion;
    private String estado;
    private Date fecha_pedido;
    private Date fecha_entrega;
    private String tipo_pago; 
    
    public Pedido() {
    }
    
    @ManyToOne
    @JoinColumn(name = "id_cliente",referencedColumnName = "id_cliente")
    private Cliente cliente;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductoPedido> listaProductosPedidos = new ArrayList<ProductoPedido>();
    
    public void addProducto(ProductoPedido productoPedido) {
        this.listaProductosPedidos.add(productoPedido);
        productoPedido.setPedido(this);
    }
}
