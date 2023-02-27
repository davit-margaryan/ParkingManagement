package parkingManagement.builder;

import parkingManagement.dto.ParkingResponseDto;
import parkingManagement.entity.Parking;

public class ParkingResponseBuilder {

    public static ParkingResponseDto buildParkingResponseDto(Parking parking) {
        ParkingResponseDto parkingResponseDto = new ParkingResponseDto();
        parkingResponseDto.setBookedOn(parking.getBookedOn());
        parkingResponseDto.setId(parking.getId());
        parkingResponseDto.setStatus(parking.getStatus());
        parkingResponseDto.setUserId(parking.getUser() != null ? parking.getUser().getId() : null);
        return parkingResponseDto;
    }
}
