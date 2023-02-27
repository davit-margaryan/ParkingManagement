package parkingManagement.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import parkingManagement.dto.ParkingRequestDto;
import parkingManagement.dto.ParkingResponseDto;
import parkingManagement.service.impl.BookServiceImpl;
import parkingManagement.service.impl.ParkingServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parkings")
public class ParkingController {
    private final ParkingServiceImpl parkingServiceImpl;
    private final BookServiceImpl bookServiceImpl;

    public ParkingController(ParkingServiceImpl parkingServiceImpl, BookServiceImpl bookServiceImpl) {
        this.parkingServiceImpl = parkingServiceImpl;
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ParkingResponseDto>> getAll() {
        Optional<List<ParkingResponseDto>> parkingResponseDtos = parkingServiceImpl.getAll();
        return parkingResponseDtos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingResponseDto>> getAvailableParks() {
        Optional<List<ParkingResponseDto>> parkingResponseDtos = parkingServiceImpl.getAvailableParks();
        return parkingResponseDtos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<ParkingResponseDto> getById(@PathVariable Long id) {
        Optional<ParkingResponseDto> parkingResponseDto = parkingServiceImpl.getById(id);
        return parkingResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public List<ParkingResponseDto> create(@RequestBody ParkingRequestDto parkingRequestDto) {
        return parkingServiceImpl.createParking(parkingRequestDto);
    }

    @PostMapping("/unbook")
    public ResponseEntity<ParkingResponseDto> unBook() {
        Optional<ParkingResponseDto> parkingResponseDto = bookServiceImpl.unBook();
        return parkingResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/book/{id}")
    public ResponseEntity<ParkingResponseDto> book(@PathVariable Long id) {
        Optional<ParkingResponseDto> parkingResponseDto = bookServiceImpl.book(id);
        return parkingResponseDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        parkingServiceImpl.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        parkingServiceImpl.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

