package se.yrgo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.yrgo.domain.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Derived query
    Optional<Customer> findByName(String name);

    // JPQL: LEFT JOIN FETCH
    //tar kunder utan fordon med
    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.vehicles")
    List<Customer> findAllWithVehicles();
}
