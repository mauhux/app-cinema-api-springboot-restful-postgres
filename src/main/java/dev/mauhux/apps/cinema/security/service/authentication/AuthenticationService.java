package dev.mauhux.apps.cinema.security.service.authentication;

import dev.mauhux.apps.cinema.security.dto.LoginRequestDto;
import dev.mauhux.apps.cinema.security.dto.LoginResponseDto;

public interface AuthenticationService {

    LoginResponseDto login(LoginRequestDto loginRequestDTO);
}
