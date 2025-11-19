package com.lisandro.gestorfinanzas.service.movement;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.lisandro.gestorfinanzas.dto.CategoryDTO.CategorySummaryDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementDTO;
import com.lisandro.gestorfinanzas.dto.Movement.MovementResponseDTO;
import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.model.Movement;
import com.lisandro.gestorfinanzas.model.Movement.Currency;
import com.lisandro.gestorfinanzas.repository.IMovementRepository;
import com.lisandro.gestorfinanzas.repository.MovementSpecifications;
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
    
        if (dto.currency() == Currency.ARS){
            // Trae el factor para saber si es + o -
            balance.setArs(balance.getArs() + dto.movementType().getFactor() * dto.amount());

        }else{
            balance.setDolares(balance.getDolares() + dto.movementType().getFactor() * dto.amount());

        }
        //Guardar balance
        balanceService.save(balance);

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
            movement.getDate(),
            new CategorySummaryDTO(
                movement.getCategory().getId(),
                movement.getCategory().getName(),
                movement.getCategory().getEmoji()
            )
        );
    }

    public Page<MovementResponseDTO> getAllMovements(String username, LocalDate startDate, LocalDate endDate, Long categoryId, Movement.MovementType type, Pageable pageable) {
        Balance balance = balanceService.findByUsername(username);

        Specification<Movement> spec = MovementSpecifications.hasBalance(balance);
        
        if (startDate != null) {
            LocalDateTime start = startDate.atStartOfDay();
            spec = spec.and(MovementSpecifications.dateStart(start));
        }

        if (endDate != null){
            LocalDateTime end = endDate.atTime(23, 59, 59);
            spec = spec.and(MovementSpecifications.dateEnd(end));
        }

        if(categoryId != null){
            Category category = categoryService.getCategoryById(categoryId, username);
            spec = spec.and(MovementSpecifications.hasCategory(category));
        }

        if (type != null){
            spec = spec.and(MovementSpecifications.hasMovementType(type));
        }

        Page<Movement> movementsPage = movementRepository.findAll(spec, pageable);
        
        return movementsPage.map(this::mapToResponseDTO);
    }
    

    @Override
    public void updateMovement() {
    
    }
}
