package com.lisandro.gestorfinanzas.service.movement;

import com.lisandro.gestorfinanzas.model.Movement;

public interface IMovementService {
    Movement saveMovement(Movement movement);

    void updateMovement();
}
