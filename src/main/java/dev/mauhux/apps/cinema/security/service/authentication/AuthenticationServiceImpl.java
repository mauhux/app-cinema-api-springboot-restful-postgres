package dev.mauhux.apps.cinema.security.service.authentication;

import dev.mauhux.apps.cinema.security.dto.LoginRequestDto;
import dev.mauhux.apps.cinema.security.dto.LoginResponseDto;
import dev.mauhux.apps.cinema.security.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDTO) {

        log.info("loginRequestDTO {}", loginRequestDTO);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.username(), loginRequestDTO.password()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.username());

        log.info("userDetails {}", userDetails);

        if (isNull(userDetails)) {
            throw new IllegalArgumentException("Invalid user or password.");
        }

        String token = jwtService.generateToken(userDetails);

        // registrar sesion exitosa

        return new LoginResponseDto(token);
    }
}
