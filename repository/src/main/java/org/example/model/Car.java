package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "vin", unique = true, nullable = false)
    private String vin;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "body_type", nullable = false)
    private String bodyType;

    @Column(name = "engine_type", nullable = false)
    private String engineType;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "transmission", nullable = false)
    private String transmission;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autosalons_cars",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "autosalon_id")
    )
    private Set<AutoSalon> autoSalons;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<DealerCar> dealerCars;

    @OneToMany(mappedBy = "car")
    private Set<TestDrive> testDrives;

    @OneToMany(mappedBy = "car")
    private Set<Sale> sales;

    public void addAutoSalon(AutoSalon autoSalon) {
        if (this.autoSalons == null) {
            this.autoSalons = new HashSet<>();
        }
        this.autoSalons.add(autoSalon);
    }
}
