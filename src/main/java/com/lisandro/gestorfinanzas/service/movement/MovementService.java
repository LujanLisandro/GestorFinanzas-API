package com.lisandro.gestorfinanzas.service.movement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.repository.IMovementRepository;

@Service
public class MovementService implements IMovementService {

    @Autowired
    private IMovementRepository movementRepository;

    @Override
    public Movement saveMovement(Movement movement) {
        return movementRepository.save(movement);
    }

    @Override
    public Movement getMovementById(Long movementId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMovementById'");
    }

    @Override
    public List<Movement> getAllMovements() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMovements'");
    }

}
