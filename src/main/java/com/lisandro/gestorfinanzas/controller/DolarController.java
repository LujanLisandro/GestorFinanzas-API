package com.lisandro.gestorfinanzas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.dto.dolar.dolarData;
import com.lisandro.gestorfinanzas.service.dolarapi.DolarService;

@RestController
@RequestMapping("api/dolar")
public class DolarController {
    
    @Autowired
    private DolarService dolarService;

    @GetMapping
    public List <dolarData> dollarsList(){
        return dolarService.getAllDollars();
    }

}
