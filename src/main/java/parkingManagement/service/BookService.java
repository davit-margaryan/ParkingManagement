package parkingManagement.service;

import parkingManagement.dto.ParkingResponseDto;
import java.util.Optional;

public interface BookService {
    Optional<ParkingResponseDto> book(Long id);

    Optional<ParkingResponseDto> unBook();
}
