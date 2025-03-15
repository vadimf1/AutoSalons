package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "dealers")
public class Dealer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dealers_contacts",
            joinColumns = @JoinColumn(name = "dealer_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private Set<Contact> contacts;

    public void addContact(Contact contact) {
        if (contacts == null) {
            contacts = new HashSet<>();
        }
        contacts.add(contact);
    }
}
