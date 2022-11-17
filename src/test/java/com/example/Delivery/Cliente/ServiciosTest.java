/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;

import com.example.Delivery.Distrito.Distrito;
import com.example.Delivery.Distrito.IDistrito;
import com.example.Delivery.Producto.IProducto;
import com.example.Delivery.Producto.Producto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 *
 * @author INTEL
 */
@DisplayName("Pruebas Unitarias")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiciosTest {
    @Autowired
    private IDistrito serviceDistritos;
    
    @Autowired
    private IProducto serviceProductos;
    
    @Test
    @DisplayName("Listar todos los distritos")
    public void testListarDistritos() throws Exception{
        String[] expResult = {"Los Olivos", "Miraflores", "Miraflores", "San Martin","Callao","Comas","Breña","Chorrillos","Carabayllo"};

        List<Distrito> result = (List<Distrito>) this.serviceDistritos.findAll();
        for (int i=0; i <= expResult.length;i++) {
            assertEquals(expResult[0],result.get(0).getNombre_distrito());
            
        }
    }
    
    @Test
    @DisplayName("Listar todos los productos")
    public void testListarProductos() throws Exception{
        System.out.println("Listar_Productos");
        
        String[] expResult = {"Ceviche", "Chupe de Camarones", "Parihuela", "Duo Marino","Trio Marino","Jalea Mixta","Sudado de Pescado","Arroz Chaufa de Mariscos","Coca Cola","Inka Kola","Chicha Morada","Chicha de Jora","Limonada","Cuzqueña","Pilsen","Cristal"};

        List<Producto> result = (List<Producto>) this.serviceProductos.findAll();
        for (int i=0; i <= expResult.length;i++) {
            assertEquals(expResult[0],result.get(0).getNombre_producto());
            
        }
    }
}
