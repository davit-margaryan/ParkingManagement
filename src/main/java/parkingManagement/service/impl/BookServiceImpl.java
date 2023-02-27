package parkingManagement.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import parkingManagement.builder.ParkingResponseBuilder;
import parkingManagement.dto.ParkingResponseDto;
import parkingManagement.entity.Parking;
import parkingManagement.entity.User;
import parkingManagement.enums.Status;
import parkingManagement.repository.ParkingRepository;
import parkingManagement.repository.UserRepository;
import parkingManagement.service.BookService;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;

    public BookServiceImpl(ParkingRepository parkingRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ParkingResponseDto> book(Long id) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isEmpty()) {
            logger.error("Parking was not found {}", id);
            return Optional.empty();
        }
        final Parking park = parking.get();
        if (park.getStatus().equals(Status.BOOKED)) {
            logger.error("The parking is booked");
            return Optional.empty();
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(name);
        userOptional.ifPresent(user -> {
            park.setBookedOn(LocalDate.now());
            park.setUser(user);
            park.setStatus(Status.BOOKED);
        });
        Parking updatedParking = parkingRepository.saveAndFlush(park);
        return Optional.of(ParkingResponseBuilder.buildParkingResponseDto(updatedParking));
    }

    @Override
    public Optional<ParkingResponseDto> unBook() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(name);
        if (userOptional.isPresent()) {
            Parking parking = userOptional.get().getParking();
            parking.setStatus(Status.FREE);
            parking.setUser(null);
            parking.setBookedOn(null);
            return Optional.of(ParkingResponseBuilder.buildParkingResponseDto(parkingRepository.save(parking)));
        }
        logger.error("You have not booked parking");
        return Optional.empty();
    }
}


