/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Delivery.Producto;

import java.util.List;

/**
 *
 * @author INTEL
 */
public interface IProductoService {
    public List<Producto> listar();
    public List<Producto> listarCategoria(int id_categoria);
}
