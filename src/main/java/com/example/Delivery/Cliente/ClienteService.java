/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Cliente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author INTEL
 */
@Service
public class ClienteService implements IClienteService{
    @Autowired
    private ICliente data;
    
    @Override
    public List<Cliente> listar() {
        return (List<Cliente>) data.findAll();
    }

    @Override
    public void Guardar(Cliente c) {
        data.save(c);
    }
    
}
