package parkingManagement;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import parkingManagement.config.JwtService;
import parkingManagement.dto.AuthResponseDto;
import parkingManagement.entity.User;
import parkingManagement.enums.Role;
import parkingManagement.repository.UserRepository;

import java.util.Optional;

@SpringBootApplication
public class ParkingManagementApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ParkingManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> admin = userRepository.findByUsername("Davit");
        if (admin.isEmpty()) {
            User user = new User();
            user.setUsername("Davit");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
    }
}

