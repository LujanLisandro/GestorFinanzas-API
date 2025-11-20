package com.lisandro.gestorfinanzas.service.movement;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;

public interface IMovementService {
    MovementResponseDTO saveMovement(MovementDTO dto, String username);

    Page<MovementResponseDTO> getAllMovements(String username, LocalDate startDate, LocalDate endDate, Long categoryId, MovementType type, Pageable pageable);

    void updateMovement(Long movementId, MovementDTO dto, String username);
}
