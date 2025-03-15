package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "autosalons")
public class AutoSalon extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(mappedBy = "autoSalons", cascade = CascadeType.ALL)
    private Set<Car> cars;

    @OneToMany
    @JoinTable(
            name = "autosalons_contacts",
            joinColumns = @JoinColumn(name = "autosalon_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts;

    @OneToMany(mappedBy = "autoSalon")
    private Set<TestDrive> testDrives;

    @OneToMany(mappedBy = "autoSalon")
    private Set<Sale> sales;

    public void addContact(Contact contact) {
        if (contacts == null) {
            contacts = new HashSet<>();
        }
        contacts.add(contact);
    }
}
