package com.lisandro.gestorfinanzas.dto.Movement;

import java.time.LocalDateTime;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategorySummaryDTO;
import com.lisandro.gestorfinanzas.model.Movement.Currency;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;

public record MovementResponseDTO(
    Long id,
    String description,
    Double amount,
    MovementType movementType,
    Currency currency,
    String reference,
    LocalDateTime date,
    CategorySummaryDTO category
) 
{}
