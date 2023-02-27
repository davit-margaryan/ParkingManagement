package parkingManagement.service;

import parkingManagement.dto.ParkingRequestDto;
import parkingManagement.dto.ParkingResponseDto;
import java.util.List;
import java.util.Optional;

public interface ParkingService {
    List<ParkingResponseDto> createParking(ParkingRequestDto parkingRequestDto);

    Optional<List<ParkingResponseDto>> getAll();

    Optional<ParkingResponseDto> getById(Long id);

    Optional<List<ParkingResponseDto>> getAvailableParks();

    void deleteAll();

    void deleteById(Long id);
}
