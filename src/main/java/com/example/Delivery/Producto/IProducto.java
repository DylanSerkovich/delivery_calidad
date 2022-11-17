/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Delivery.Producto;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author INTEL
 */
@Repository
public interface IProducto extends CrudRepository<Producto, Integer>{
    
    @Query(value="SELECT * FROM Producto p WHERE p.id_categoria=?1",nativeQuery=true)
    List<Producto> findByCategoria(int id_categoria);
}
