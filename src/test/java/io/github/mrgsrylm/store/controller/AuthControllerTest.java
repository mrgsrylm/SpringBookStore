package io.github.mrgsrylm.store.controller;

import io.github.mrgsrylm.store.base.BaseControllerTest;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.enums.Role;
import io.github.mrgsrylm.store.payload.request.auth.LoginRequest;
import io.github.mrgsrylm.store.payload.request.auth.SignupRequest;
import io.github.mrgsrylm.store.payload.request.auth.TokenRefreshRequest;
import io.github.mrgsrylm.store.payload.response.auth.JWTResponse;
import io.github.mrgsrylm.store.payload.response.auth.TokenRefreshResponse;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest extends BaseControllerTest {
    @MockBean
    private AuthServiceImpl authService;


    @Test
    void givenSignupRequest_WhenCustomerRole_ReturnSuccess() throws Exception {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("customer_fullname")
                .password("customer_password")
                .username("customer_1")
                .email("customer@springbookstore.com")
                .role(Role.ROLE_CUSTOMER)
                .build();

        when(authService.register(request)).thenReturn("success");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

    @Test
    void givenSignupRequest_WhenAdminRole_ReturnSuccess() throws Exception {

        // given
        SignupRequest request = SignupRequest.builder()
                .fullName("admin_fullname")
                .password("admin_password")
                .username("admin_1")
                .email("admin@springbookstore.com")
                .role(Role.ROLE_ADMIN)
                .build();

        when(authService.register(request)).thenReturn("success");

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

    }

    @Test
    void givenLoginRequest_WhenCustomerRole_ReturnSuccess() throws Exception {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("customer@springbookstore.com")
                .password("customer_password")
                .build();

        JWTResponse mockResponse = JWTResponse.builder()
                .email(request.getEmail())
                .token("mockedToken")
                .refreshToken("mockedRefreshToken")
                .build();

        // when
        when(authService.login(request)).thenReturn(mockResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void givenRefreshTokenRequestAndAccessToken_WhenCustomerRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // given
        User mockUser = User.builder()
                .id(1L)
                .username("customer_1")
                .email("customer@springbookstore.com")
                .role(Role.ROLE_CUSTOMER)
                .fullName("customer_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        TokenRefreshResponse mockResponse = TokenRefreshResponse.builder()
                .accessToken("newMockedToken")
                .refreshToken("validRefreshToken")
                .build();

        // when
        when(authService.refreshToken(request)).thenReturn(mockResponse);
        when(customUserDetailsService.loadUserByUsername("customer@springbookstore.com")).thenReturn(userDetails);

        // then
        mockMvc.perform(post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void givenAccessToken_WhenCustomerRole_ReturnLogoutSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id(1L)
                .username("customer_1")
                .email("customer@springbookstore.com")
                .role(Role.ROLE_CUSTOMER)
                .fullName("customer_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        when(customUserDetailsService.loadUserByUsername("customer@springbookstore.com")).thenReturn(userDetails);
        when(authService.logout(mockBearerToken)).thenReturn("success");

        // Then
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(status().isOk());

        verify(authService).logout(mockBearerToken);

    }

    @Test
    void givenLoginRequest_WhenAdminRole_ReturnSuccess() throws Exception {

        // given
        LoginRequest request = LoginRequest.builder()
                .email("admin@springbookstore.com")
                .password("admin_password")
                .build();

        JWTResponse mockResponse = JWTResponse.builder()
                .email(request.getEmail())
                .token("mockedToken")
                .refreshToken("mockedRefreshToken")
                .build();

        // when
        when(authService.login(request)).thenReturn(mockResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void givenRefreshTokenRequestandAccessToken_WhenAdminRole_Token_ReturnRefreshTokenSuccess() throws Exception {

        // given
        User mockUser = User.builder()
                .id(2L)
                .username("admin_1")
                .email("admin@springbookstore.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        TokenRefreshRequest request = TokenRefreshRequest.builder()
                .refreshToken("validRefreshToken")
                .build();

        TokenRefreshResponse mockResponse = TokenRefreshResponse.builder()
                .accessToken("newMockedToken")
                .refreshToken("validRefreshToken")
                .build();

        // when
        when(authService.refreshToken(request)).thenReturn(mockResponse);
        when(customUserDetailsService.loadUserByUsername("admin@springbookstore.com")).thenReturn(userDetails);

        // then
        mockMvc.perform(post("/api/v1/auth/refreshtoken")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void givenAccessToken_WhenAdminRole_ReturnLogoutSuccess() throws Exception {

        // Given
        User mockUser = User.builder()
                .id(2L)
                .username("admin_1")
                .email("admin@springbookstore.com")
                .role(Role.ROLE_ADMIN)
                .fullName("admin_fullname")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(mockUser);


        String accessToken = jwtUtils.generateJwtToken(userDetails);

        String mockBearerToken = "Bearer " + accessToken;

        // When
        when(customUserDetailsService.loadUserByUsername("admin@springbookstore.com")).thenReturn(userDetails);
        when(authService.logout(mockBearerToken)).thenReturn("success");

        // Then
        mockMvc.perform(post("/api/v1/auth/logout")
                        .header(HttpHeaders.AUTHORIZATION, mockBearerToken))
                .andExpect(status().isOk());

        verify(authService).logout(mockBearerToken);

    }
}