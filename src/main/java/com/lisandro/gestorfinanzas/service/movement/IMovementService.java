package com.lisandro.gestorfinanzas.service.movement;

import java.util.List;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;

public interface IMovementService {
    MovementResponseDTO saveMovement(MovementDTO dto, String username);

    List<MovementResponseDTO> getAllMovements(String username);
    void updateMovement();
}
