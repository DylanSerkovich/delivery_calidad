/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.ProductoPedido;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 *
 * @author INTEL
 */
@Embeddable
@Data
public class ProductoPedidoID implements Serializable{
    private int id_pedido;
    private int id_producto;

    public ProductoPedidoID() {
    }
    
    public ProductoPedidoID( int id_producto) {
        this.id_producto = id_producto;
    }
}
