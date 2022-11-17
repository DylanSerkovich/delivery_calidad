/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.ProductoPedido;

import com.example.Delivery.Pedido.Pedido;
import com.example.Delivery.Producto.Producto;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author INTEL
 */
@Data
@Entity
@Table(name = "PRODUCTO_PEDIDO")
public class ProductoPedido {
    @EmbeddedId
    private ProductoPedidoID id;
    
    private int cantidad;
    private float precio_unitario;

    @ManyToOne
    @MapsId("id_pedido")
    @JoinColumn(name = "id_pedido",referencedColumnName = "id_pedido")
    private Pedido pedido;

    @OneToOne
    @MapsId("id_producto")
    @JoinColumn(name = "id_producto",referencedColumnName = "id_producto")
    private Producto producto;

    public ProductoPedido() {
    }
    
    public ProductoPedido(int cantidad, float precio_unitario, Producto producto) {
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.producto = producto;
    }
    
}
