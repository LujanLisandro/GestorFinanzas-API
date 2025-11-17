package com.lisandro.gestorfinanzas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;
import com.lisandro.gestorfinanzas.service.movement.IMovementService;

@RestController
@RequestMapping("api/movement")
public class MovementController {
    @Autowired
    IMovementService movementService;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(@RequestBody MovementDTO movementDTO, Authentication auth){
        MovementResponseDTO createdMovement = movementService.saveMovement(movementDTO, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
    }

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> getMovementsByUser(
        Authentication auth,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) MovementType type
    ){

        return ResponseEntity.status(HttpStatus.OK).body(movementService.getAllMovements(
            auth.getName(),
            startDate,
            endDate,
            categoryId,
            type
        ));
    }
}
