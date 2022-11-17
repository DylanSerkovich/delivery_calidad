/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Delivery.Cliente;

import java.util.List;

/**
 *
 * @author INTEL
 */
public interface IClienteService {
    public List<Cliente> listar();
    public void Guardar(Cliente c);
}
