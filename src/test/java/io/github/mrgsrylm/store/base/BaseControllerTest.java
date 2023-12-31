package io.github.mrgsrylm.store.base;

import io.github.mrgsrylm.store.builder.UserBuilder;
import io.github.mrgsrylm.store.logging.entity.LogEntity;
import io.github.mrgsrylm.store.logging.service.impl.LogServiceImpl;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.security.CustomUserDetails;
import io.github.mrgsrylm.store.security.CustomUserDetailsService;
import io.github.mrgsrylm.store.security.jwt.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseControllerTest extends AbstractTestContainerConfiguration {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CustomUserDetailsService customUserDetailsService;

    @MockBean
    protected LogServiceImpl logService;

    @Autowired
    protected JwtUtils jwtUtils;

    protected User mockUser;

    protected String mockUserToken;

    protected User mockAdmin;

    protected String mockAdminToken;


    @BeforeEach
    protected void initializeAuth() {

        this.mockUser = new UserBuilder().customer().build();
        this.mockAdmin = new UserBuilder().admin().build();

        final CustomUserDetails mockUserDetails = new CustomUserDetails(mockUser);
        final CustomUserDetails mockAdminDetails = new CustomUserDetails(mockAdmin);

        this.mockUserToken = generateMockToken(mockUserDetails);
        this.mockAdminToken = generateMockToken(mockAdminDetails);
        Mockito.when(customUserDetailsService.loadUserByUsername(mockUser.getEmail())).thenReturn(mockUserDetails);
        Mockito.when(customUserDetailsService.loadUserByUsername(mockAdmin.getEmail())).thenReturn(mockAdminDetails);
        Mockito.doNothing().when(logService).saveLogToDatabase(any(LogEntity.class));
    }

    private String generateMockToken(CustomUserDetails details) {
        return "Bearer ".concat(jwtUtils.generateJwtToken(details));
    }
}
