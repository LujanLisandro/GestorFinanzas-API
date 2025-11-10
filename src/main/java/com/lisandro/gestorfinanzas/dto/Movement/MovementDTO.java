package com.lisandro.gestorfinanzas.dto.Movement;

import com.lisandro.gestorfinanzas.model.Movement.Currency;
import com.lisandro.gestorfinanzas.model.Movement.MovementType;

public record MovementDTO(
    String description, 
    Double amount, 
    MovementType movementType, 
    Currency currency, 
    Long categoryID
    )
{}
