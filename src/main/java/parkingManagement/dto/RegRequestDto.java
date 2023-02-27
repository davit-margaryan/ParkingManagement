package parkingManagement.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegRequestDto {

    @NotNull
    @Size(min = 4,max = 20, message = "user name should have at least 4 characters")
    private String username;
    @NotNull
    @Size(min = 8, max = 20,message = "password should have at least 8 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}