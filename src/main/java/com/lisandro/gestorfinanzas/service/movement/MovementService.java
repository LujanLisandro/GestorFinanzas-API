package com.lisandro.gestorfinanzas.service.movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.repository.IMovementRepository;
@Service
public class MovementService implements IMovementService {

    @Autowired
    IMovementRepository movementRepository;

    @Override
    public Movement saveMovement(Movement movement) {
        return movementRepository.save(movement);
    }

    @Override
    public void updateMovement() {
    
    }

}
