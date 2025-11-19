package com.lisandro.gestorfinanzas.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.model.Category;
import com.lisandro.gestorfinanzas.model.Movement;

public class MovementSpecifications {
    public static Specification<Movement> hasBalance(Balance balance){
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get("balance"), balance);

    }

    public static Specification<Movement> dateStart(LocalDateTime startDate){
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    public static Specification<Movement> dateEnd (LocalDateTime endDate){
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate);
    }

    public static Specification<Movement> hasCategory (Category category){
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Movement> hasMovementType (Movement.MovementType type){
        return (root,query,criteriaBuilder) ->
            criteriaBuilder.equal(root.get("movementType"), type);
    }

}
