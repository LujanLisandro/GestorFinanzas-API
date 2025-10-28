package com.lisandro.gestorfinanzas.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double ars;
    private double dolares;

    // Relacion
    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stock> stockList;

    @OneToOne(mappedBy = "balance")
    private UserSec user;

}
