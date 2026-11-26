package se.yrgo.domain.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.yrgo.domain.entity.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Derived query
    List<Vehicle> findByBrand(String brand);

    // JPQL: fordon för en viss kund
    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId")
    List<Vehicle> findByCustomerId(@Param("customerId") Long customerId);

    // Uppdatering med @Modifying
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Vehicle v SET v.brand = :brand WHERE v.id = :id")
    int updateBrand(@Param("id") Long id, @Param("brand") String brand);

    // Projection-fråga
    @Query("SELECT v.registrationNumber AS registrationNumber, v.brand AS brand FROM Vehicle v")
    List<VehicleBrandProjection> findAllBrandInfo();
}
