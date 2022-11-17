/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Producto;

import com.example.Delivery.Categoria.Categoria;
import com.example.Delivery.ProductoPedido.ProductoPedido;
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
@Table(name = "PRODUCTO")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;
    private String dir_imagen;
    private String nombre_producto;  
    private String descripcion;
    private float precio_unitario;

    public Producto() {
    }
    
    public Producto(String dir_imagen, Categoria categoria, String nombre_producto, String descripcion, float precio_unitario) {
        this.dir_imagen = dir_imagen;
        this.categoria = categoria;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.precio_unitario = precio_unitario;
    }
    
    @ManyToOne
    @JoinColumn(name = "id_categoria",referencedColumnName = "id_categoria")
    private Categoria categoria;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<ProductoPedido> listaProductos = new ArrayList<ProductoPedido>();
}
