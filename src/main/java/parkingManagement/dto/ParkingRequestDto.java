package parkingManagement.dto;

import jakarta.annotation.Nonnull;
import org.springframework.validation.annotation.Validated;

@Validated
public class ParkingRequestDto {
    @Nonnull
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
