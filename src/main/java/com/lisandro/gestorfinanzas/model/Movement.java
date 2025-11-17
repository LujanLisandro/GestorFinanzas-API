package com.lisandro.gestorfinanzas.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos")
@Entity
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double amount;

    private MovementType movementType;

    private Currency currency;
    
    private String reference;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "balance_id", nullable = false)
    @JsonBackReference("balance-movements")  // Debe coincidir con el nombre en Balance
    private Balance balance;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference("category-movements")  // Debe coincidir con el nombre en Category
    private Category category;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }

    public enum Currency {
        ARS("Peso Argentino"),
        USD("Dolar");

        private final String description;

        Currency(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum MovementType {
        INGRESO(1,"Ingreso"),
        EGRESO(-1,"Egreso");

        private final int factor;
        private final String description;

        MovementType(int factor,String description) {
            this.factor = factor;
            this.description = description;
        }

        public int getFactor(){
            return factor;
        }

        public String getDescription() {
            return description;
        }
    }
}
