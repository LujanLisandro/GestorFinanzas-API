package com.lisandro.gestorfinanzas.service.movement;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.model.Movement;

public interface IMovementService {
    Movement saveMovement(MovementDTO dto, String username);

    void updateMovement();
}
