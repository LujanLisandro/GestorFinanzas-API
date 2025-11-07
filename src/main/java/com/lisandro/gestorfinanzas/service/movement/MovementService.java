package com.lisandro.gestorfinanzas.service.movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.model.UserSec;
import com.lisandro.gestorfinanzas.repository.IMovementRepository;
import com.lisandro.gestorfinanzas.service.balance.IBalanceService;
import com.lisandro.gestorfinanzas.service.user.IUserService;
@Service
public class MovementService implements IMovementService {

    @Autowired
    private IMovementRepository movementRepository;
    @Autowired
    private IBalanceService balanceService;

    @Override
    public Movement saveMovement(MovementDTO dto, String username) {
        Balance balance = balanceService.findByUsername(username); 
        Movement movement = new Movement();
        movement.setBalance(balance);
        movement.setAmount(dto.amount());
        movement.setCategory(dto.);
        return movementRepository.save(movement);
    }

    @Override
    public void updateMovement() {
    
    }

}
