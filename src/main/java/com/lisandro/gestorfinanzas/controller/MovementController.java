package com.lisandro.gestorfinanzas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.service.movement.IMovementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
public class MovementController {

    @Autowired
    private IMovementService movementService;

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody Movement movement){
        movementService.saveMovement(movement);
        return ResponseEntity.ok(movement);
    }
    
}
