package com.lisandro.gestorfinanzas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.service.movement.IMovementService;

@RestController
@RequestMapping("api/movement")
public class MovementController {
    @Autowired
    IMovementService movementService;

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody MovementDTO movementDTO, Authentication auth){
        Movement createdMovement = movementService.saveMovement(movementDTO, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
    }
}
