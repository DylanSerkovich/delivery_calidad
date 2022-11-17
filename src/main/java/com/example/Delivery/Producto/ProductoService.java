/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Producto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author INTEL
 */
@Service
public class ProductoService implements IProductoService{
    @Autowired
    private IProducto data;
    
    @Override
    public List<Producto> listar() {
        return (List<Producto>) data.findAll();
    }
    
    @Override
    public List<Producto> listarCategoria(int id_categoria) {
        return (List<Producto>) data.findByCategoria(id_categoria);
    }

}
