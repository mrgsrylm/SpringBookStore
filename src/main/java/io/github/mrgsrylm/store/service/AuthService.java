package io.github.mrgsrylm.store.service;

import io.github.mrgsrylm.store.payload.request.auth.LoginRequest;
import io.github.mrgsrylm.store.payload.request.auth.SignupRequest;
import io.github.mrgsrylm.store.payload.request.auth.TokenRefreshRequest;
import io.github.mrgsrylm.store.payload.response.auth.JWTResponse;
import io.github.mrgsrylm.store.payload.response.auth.TokenRefreshResponse;

/**
 * This interface defines authentication and authorization services.
 */
public interface AuthService {
    /**
     * Registers a new user based on the provided signup request.
     *
     * @param request The signup request containing user registration information.
     * @return A string representing the result of the registration process.
     */
    String register(SignupRequest request);

    /**
     * Logs a user in using the provided login credentials.
     *
     * @param request The login request containing user login credentials.
     * @return A {@link JWTResponse} containing a JWT token and related information upon successful login.
     */
    JWTResponse login(LoginRequest request);

    /**
     * Refreshes a user's authentication token.
     *
     * @param request The token refresh request containing the old token.
     * @return A {@link TokenRefreshResponse} containing a new JWT token upon successful token refresh.
     */
    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    /**
     * Logs a user out by invalidating their token.
     *
     * @param token The user's authentication token to be invalidated.
     * @return A string representing the result of the logout process.
     */
    String logout(String token);
}
