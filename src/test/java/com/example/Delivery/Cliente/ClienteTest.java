/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.Delivery.Cliente;

import com.example.Delivery.Distrito.Distrito;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("Pruebas Unitarias")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteTest {
    
    @Autowired
    private ICliente serviceClientes;  
    
    @Test
    @DisplayName("Listar todos los clientes")
    public void testListarClientes() throws Exception{
        System.out.println("Listar_Clientes");
        
        Distrito distrito1 = new Distrito(1, "Los Olivos", "15");
        Distrito distrito2 = new Distrito(5, "Callao", "15");
        
        Cliente instance1 = new Cliente("Dilam Anderson", "Chuquilin Serkovich", "98765432", "N00189659@upn.pe", "902755122", distrito1, "Av. Alfredo Mendiola 6232", "Anderson21", "contra01");
        Cliente instance2 = new Cliente("Anthony Ivan", "Palacios Martel", "99887766", "N00169447@upn.pe", "940205924", distrito2, "Av. María Parado De Bellido", "Palacios23", "contra02");
        instance1.setId_cliente(1);
        instance2.setId_cliente(2);
        
        List<Cliente> expResult = Arrays.asList(instance1,instance2);
        List<Cliente> result = (List<Cliente>) this.serviceClientes.findAll();
        
        assertEquals(expResult.get(0).getUsuario(),result.get(0).getUsuario());
        assertEquals(expResult.get(0).getClave(),result.get(0).getClave());
        
        assertEquals(expResult.get(1).getUsuario(),result.get(1).getUsuario());
        assertEquals(expResult.get(1).getClave(),result.get(1).getClave());
    }
    
    @Test
    @DisplayName("Registro de un cliente")
    public void testRegistrarCliente() throws Exception{
        System.out.println("Registrar_Cliente");
        Distrito distrito = new Distrito(1, "Los Olivos", "15");
        Cliente instance = new Cliente("Henry Gonzalo", "Mejia Diaz", "98765432", "N00206130@upn.pe", "960717757", distrito, "Av. Alfredo Mendiola 6232", "Mejia20", "contra02");
        
        Cliente nuevoRegistro = this.serviceClientes.save(instance);
        
        assertNotNull(nuevoRegistro);
    }
    
    @Test
    @DisplayName("Actualizar un cliente")
    public void testActualizarCliente() throws Exception{
        System.out.println("Actualizar_Cliente_ID");
        Distrito distrito = new Distrito(6, "Comas", "19");
        Cliente instance = new Cliente("Luis Angel", "Muchari Gil", "12345678", "N00204526@upn.pe", "984367488", distrito, "Av. Maestro Peruano 199", "Muchari20", "contra03");
        instance.setId_cliente(1);
        
        Cliente clienteActualizado= this.serviceClientes.save(instance);
        assertEquals(instance.getNombres(), clienteActualizado.getNombres());
    }
    
    @Test
    @DisplayName("Eliminar un cliente por id")
    public void testEliminarClienteID() throws Exception{
        System.out.println("Eliminar_Cliente_ID");
        boolean existeAntes = this.serviceClientes.findById(1).isPresent();
        this.serviceClientes.deleteById(1);
        boolean existeDespues = this.serviceClientes.findById(1).isPresent();
        
        assertTrue(existeAntes);
        assertFalse(existeDespues);
    }
    
}
