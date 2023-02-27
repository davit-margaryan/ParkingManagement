package parkingManagement.service;

import jakarta.transaction.Transactional;
import parkingManagement.config.JwtService;
import parkingManagement.dto.AuthRequestDto;
import parkingManagement.dto.AuthResponseDto;
import parkingManagement.dto.RegRequestDto;
import parkingManagement.enums.Role;
import parkingManagement.entity.User;
import parkingManagement.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AuthResponseDto> login(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequestDto.getUsername(),
                authRequestDto.getPassword()));
        Optional<User> user = userRepository.findByUsername(authRequestDto.getUsername());
        if(user.isPresent()){
            String jwtToken = jwtService.generateToken(user.get());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccessToken(jwtToken);
            return Optional.of(authResponseDto);
        }
       return Optional.empty();
    }

    public AuthResponseDto register(RegRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(jwtToken);
        return authResponseDto;
    }
}

