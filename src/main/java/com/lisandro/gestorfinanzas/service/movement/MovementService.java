package com.lisandro.gestorfinanzas.service.movement;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        movement.setReference(dto.reference());
        movement.setCategory(category);
        movement.setDate(dto.date()); // Si viene null, @PrePersist asigna fecha actual
        
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

        // Si no viene ordenamiento, ordenar por fecha descendente (más recientes primero)
        Pageable sortedPageable = pageable.getSort().isSorted() 
            ? pageable 
            : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "date"));

        Page<Movement> movementsPage = movementRepository.findAll(spec, sortedPageable);
        
        return movementsPage.map(this::mapToResponseDTO);
    }
    

    @Override
    public MovementResponseDTO updateMovement(Long movementId, MovementDTO dto, String username) {
        // 1. Buscar movimiento
        Movement existingMovement = movementRepository.findById(movementId)
            .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        // 2. Buscar balance y verificar permisos
        Balance balance = balanceService.findByUsername(username);
        
        if (!existingMovement.getBalance().equals(balance)){
            throw new RuntimeException("No tienes permisos para modificar este movimiento");
        }

        // 3. REVERTIR el impacto del movimiento anterior en el balance
        double oldImpact = existingMovement.getMovementType().getFactor() * existingMovement.getAmount();
        if (existingMovement.getCurrency() == Currency.ARS) {
            balance.setArs(balance.getArs() - oldImpact);
        } else {
            balance.setDolares(balance.getDolares() - oldImpact);
        }

        // 4. APLICAR el impacto del nuevo movimiento al balance
        double newImpact = dto.movementType().getFactor() * dto.amount();
        if (dto.currency() == Currency.ARS) {
            balance.setArs(balance.getArs() + newImpact);
        } else {
            balance.setDolares(balance.getDolares() + newImpact);
        }
        
        // 5. Guardar balance actualizado
        balanceService.save(balance);

        // 6. Buscar nueva categoría
        Category category = categoryService.getCategoryById(dto.categoryID(), username);
        
        // 7. Actualizar campos del movimiento
        existingMovement.setAmount(dto.amount());
        existingMovement.setCategory(category);
        existingMovement.setCurrency(dto.currency());
        existingMovement.setDescription(dto.description());
        existingMovement.setMovementType(dto.movementType());
        existingMovement.setDate(dto.date());
        existingMovement.setReference(username); // Se mantiene el username del usuario autenticado
        
        // 8. Guardar movimiento actualizado
        Movement updated = movementRepository.save(existingMovement);
        
        // 9. Retornar respuesta
        return mapToResponseDTO(updated);
    }
}
