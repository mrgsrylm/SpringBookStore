package io.github.mrgsrylm.store.controller;

import io.github.mrgsrylm.store.payload.request.auth.LoginRequest;
import io.github.mrgsrylm.store.payload.request.auth.SignupRequest;
import io.github.mrgsrylm.store.payload.request.auth.TokenRefreshRequest;
import io.github.mrgsrylm.store.payload.response.CustomResponse;
import io.github.mrgsrylm.store.payload.response.auth.JWTResponse;
import io.github.mrgsrylm.store.payload.response.auth.TokenRefreshResponse;
import io.github.mrgsrylm.store.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<String> register(@RequestBody SignupRequest request) {

        return CustomResponse.created(authService.register(request));
    }

    @PostMapping("/login")
    public CustomResponse<JWTResponse> login(@RequestBody LoginRequest request) {

        return CustomResponse.ok(authService.login(request));
    }

    @PostMapping("/refreshtoken")
    public CustomResponse<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {

        return CustomResponse.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public CustomResponse<String> logout(@RequestHeader("Authorization") String token) {

        return CustomResponse.ok(authService.logout(token));
    }
}
