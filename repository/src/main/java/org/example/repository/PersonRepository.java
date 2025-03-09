package org.example.repository;

import org.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFirstName(String firstName);

    boolean existsByFirstName(String firstName);

    @Query("SELECT p FROM Person p JOIN p.contacts c GROUP BY p HAVING COUNT(c) = :count")
    List<Person> getPersonsWithContactsCount(int count);
}
