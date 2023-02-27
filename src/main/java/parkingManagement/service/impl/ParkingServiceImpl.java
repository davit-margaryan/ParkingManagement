package parkingManagement.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import parkingManagement.builder.ParkingResponseBuilder;
import parkingManagement.dto.ParkingRequestDto;
import parkingManagement.dto.ParkingResponseDto;
import parkingManagement.entity.Parking;
import parkingManagement.repository.ParkingRepository;
import parkingManagement.enums.Status;
import parkingManagement.service.ParkingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<ParkingResponseDto> createParking(ParkingRequestDto parkingRequestDto) {
        List<Parking> parkings = new ArrayList<>();
        for (int i = 0; i < parkingRequestDto.getCount(); i++) {
            Parking parking = new Parking();
            parking.setStatus(Status.FREE);
            parkings.add(parking);
        }
        List<Parking> saved = parkingRepository.saveAll(parkings);
        return saved.stream().map(ParkingResponseBuilder::buildParkingResponseDto).collect(Collectors.toList());
    }


    @Override
    public Optional<List<ParkingResponseDto>> getAll() {
        List<ParkingResponseDto> response = new ArrayList<>();
        List<Parking> all = parkingRepository.findAll();
        if (all.isEmpty()) {
            logger.error("Parkings are not registered");
            return Optional.empty();
        }
        for (Parking parking : all) {
            response.add(ParkingResponseBuilder.buildParkingResponseDto(parking));
        }
        return Optional.of(response);
    }

    @Override
    public Optional<ParkingResponseDto> getById(Long id) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isEmpty()) {
            logger.error("The parking is not found");
            return Optional.empty();
        }
        return Optional.of(ParkingResponseBuilder.buildParkingResponseDto(parking.get()));
    }

    @Override
    public Optional<List<ParkingResponseDto>> getAvailableParks() {
        List<Parking> availableParks = parkingRepository.getAvailableParks();
        if (availableParks.isEmpty()) {
            logger.error("There is not available parks");
            return Optional.empty();
        }
        return Optional.of(availableParks.stream().map(ParkingResponseBuilder::buildParkingResponseDto).collect(Collectors.toList()));
    }

    @Override
    public void deleteAll() {
        parkingRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        parkingRepository.deleteById(id);
    }
}
