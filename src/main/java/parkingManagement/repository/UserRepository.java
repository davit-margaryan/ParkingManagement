package parkingManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import parkingManagement.entity.User;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
