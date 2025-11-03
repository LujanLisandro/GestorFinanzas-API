package com.lisandro.gestorfinanzas.service.movement;

import java.util.List;

import com.lisandro.gestorfinanzas.model.Movement;

public interface IMovementService {
    Movement saveMovement(Movement movement);

    Movement getMovementById(Long movementId);

    List<Movement> getAllMovements();
}
