package com.lisandro.gestorfinanzas.service.movement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategorySummaryDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;
import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.repository.IMovementRepository;
import com.lisandro.gestorfinanzas.service.balance.IBalanceService;
import com.lisandro.gestorfinanzas.service.category.ICategoryService;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
@Service
public class MovementService implements IMovementService {

    @Autowired
    private IMovementRepository movementRepository;
    @Autowired
    private IBalanceService balanceService;
    @Autowired
    private ICategoryService categoryService;

    @Override
    public MovementResponseDTO saveMovement(MovementDTO dto, String username) {
        Balance balance = balanceService.findByUsername(username);
        Category category = categoryService.getCategoryById(dto.categoryID(), username);
        
        Movement movement = new Movement();
        movement.setBalance(balance);
        movement.setAmount(dto.amount());
        movement.setDescription(dto.description());
        movement.setMovementType(dto.movementType());
        movement.setCurrency(dto.currency());
        movement.setReference(username);
        movement.setCategory(category);
        // fecha se asigna automáticamente por @PrePersist
        
        Movement savedMovement = movementRepository.save(movement);
        
        // Mapear a DTO para retornar
        return mapToResponseDTO(savedMovement);
    }
    
    // Método helper para mapear Movement → MovementResponseDTO
    private MovementResponseDTO mapToResponseDTO(Movement movement) {
        return new MovementResponseDTO(
            movement.getId(),
            movement.getDescription(),
            movement.getAmount(),
            movement.getMovementType(),
            movement.getCurrency(),
            movement.getReference(),
            movement.getFecha(),
            new CategorySummaryDTO(
                movement.getCategory().getId(),
                movement.getCategory().getName(),
                movement.getCategory().getEmoji()
            )
        );
    }

    public List<MovementResponseDTO> getAllMovements(String username) {
        Balance balance = balanceService.findByUsername(username);
        List<Movement> movements = balance.getMovements();
        
        return movements.stream()
            .map(this::mapToResponseDTO)
            .toList();
    }
    

    @Override
    public void updateMovement() {
    
    }
}
