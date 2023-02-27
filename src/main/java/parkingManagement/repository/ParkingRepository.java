package parkingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import parkingManagement.entity.Parking;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    @Query(value = "select p from parking p where p.status = FREE}", nativeQuery = true)
    List<Parking> getAvailableParks();

}
