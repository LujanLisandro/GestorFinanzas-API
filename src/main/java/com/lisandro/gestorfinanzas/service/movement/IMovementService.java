package com.lisandro.gestorfinanzas.service.movement;

import java.time.LocalDate;
import java.util.List;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;

public interface IMovementService {
    MovementResponseDTO saveMovement(MovementDTO dto, String username);

    List<MovementResponseDTO> getAllMovements(String username, LocalDate startDate, LocalDate endDate, Long categoryId, MovementType type);
    void updateMovement();
}
