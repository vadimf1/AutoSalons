package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "autosalon_id")
    private AutoSalon autoSalon;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "sale_date")
    private LocalDate saleDate;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "warranty_period")
    private int warrantyPeriod;
}
