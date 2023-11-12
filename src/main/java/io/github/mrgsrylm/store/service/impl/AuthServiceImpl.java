package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.exception.token.RefreshTokenNotFoundException;
import io.github.mrgsrylm.store.exception.user.EmailAlreadyExistsException;
import io.github.mrgsrylm.store.exception.user.UserNotFoundException;
import io.github.mrgsrylm.store.model.RefreshToken;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.payload.request.auth.LoginRequest;
import io.github.mrgsrylm.store.payload.request.auth.SignupRequest;
import io.github.mrgsrylm.store.payload.request.auth.TokenRefreshRequest;
import io.github.mrgsrylm.store.payload.response.auth.JWTResponse;
import io.github.mrgsrylm.store.payload.response.auth.TokenRefreshResponse;
import io.github.mrgsrylm.store.repository.UserRepository;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.security.jwt.JwtUtils;
import io.github.mrgsrylm.store.service.AuthService;
import io.github.mrgsrylm.store.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    /**
     * Registers a new user based on the provided signup request.
     *
     * @param request The signup request containing user registration information.
     * @return A string representing the result of the registration process.
     */
    @Override
    public String register(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "success";
    }

    /**
     * Logs a user in using the provided login credentials.
     *
     * @param request The login request containing user login credentials.
     * @return A {@link JWTResponse} containing a JWT token and related information upon successful login.
     */
    @Override
    public JWTResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtUtils.generateJwtToken(auth);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);

        return JWTResponse.builder()
                .email(request.getEmail())
                .token(jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(user))
                .build();
    }

    /**
     * Refreshes a user's authentication token.
     *
     * @param request The token refresh request containing the old token.
     * @return A {@link TokenRefreshResponse} containing a new JWT token upon successful token refresh.
     */
    @Override
    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(RefreshTokenNotFoundException::new);


        if (!refreshTokenService.isRefreshExpired(refreshToken)) {
            CustomUserDetails customUserDetails = new CustomUserDetails(refreshToken.getUser());
            String newToken = jwtUtils.generateJwtToken(customUserDetails);

            return TokenRefreshResponse.builder()
                    .accessToken(newToken)
                    .refreshToken(refreshToken.getToken())
                    .build();
        }

        return null;
    }

    /**
     * Logs a user out by invalidating their token.
     *
     * @param token The user's authentication token to be invalidated.
     * @return A string representing the result of the logout process.
     */
    @Override
    public String logout(String token) {

        String authToken = jwtUtils.extractTokenFromHeader(token);

        if (jwtUtils.validateJwtToken(authToken)) {
            Long id = jwtUtils.getIdFromToken(authToken);

            refreshTokenService.deleteByUserId(id);

            return "success";
        }

        return "failed";
    }
}
