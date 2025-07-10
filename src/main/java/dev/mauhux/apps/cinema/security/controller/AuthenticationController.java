package dev.mauhux.apps.cinema.security.controller;

import dev.mauhux.apps.cinema.security.dto.LoginRequestDto;
import dev.mauhux.apps.cinema.security.dto.LoginResponseDto;
import dev.mauhux.apps.cinema.security.service.authentication.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> signinHeader(HttpServletResponse response,
                                                         @Validated @RequestBody LoginRequestDto request) {

        String token = authenticationService.login(request).token();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(responseHeaders).build();
    }

    @PostMapping("/login-body")
    public ResponseEntity<Map<String, String>> signinBody(HttpServletResponse response,
                                                          @RequestBody LoginRequestDto request) {
        String token = authenticationService.login(request).token();
        log.info("token {}", token);

        Map<String, String> resp = new HashMap<>();
        resp.put("token", token);

        return ResponseEntity.ok().body(resp);
    }
}
