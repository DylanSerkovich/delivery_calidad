/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery.Distrito;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author INTEL
 */
@Service
public class DistritoService implements IDistritoService{
    @Autowired
    private IDistrito data;
    
    @Override
    public List<Distrito> listar() {
        return (List<Distrito>) data.findAll();
    }
}
