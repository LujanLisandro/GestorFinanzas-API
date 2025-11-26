package com.lisandro.gestorfinanzas.dto.Movement;
import java.time.LocalDateTime;

import com.lisandro.gestorfinanzas.model.Movement.Currency;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;

public record MovementDTO(
    String description, 
    Double amount, 
    MovementType movementType,
    String reference,
    Currency currency, 
    Long categoryID,
    LocalDateTime date
    )
{}
