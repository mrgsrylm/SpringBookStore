package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.base.BaseServiceTest;
import io.github.mrgsrylm.store.dto.UserDTO;
import io.github.mrgsrylm.store.exception.user.EmailAlreadyExistsException;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.enums.Role;
import io.github.mrgsrylm.store.model.mapper.user.UserMapper;
import io.github.mrgsrylm.store.payload.request.customer.CustomerCreateRequest;
import io.github.mrgsrylm.store.repository.UserRepository;
import io.github.mrgsrylm.store.util.RandomUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

class CustomerServiceImplTest extends BaseServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void givenValidCreateCustomerRequest_whenSuccess_thenReturnUserDto() {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@springbookstore.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        User user = User.builder()
                .id(1L)
                .email(mockRequest.getEmail())
                .fullName(mockRequest.getFullName())
                .username(mockRequest.getUsername())
                .role(Role.ROLE_CUSTOMER)
                .password(mockRequest.getPassword())
                .build();

        UserDTO userDTO = UserMapper.toDTO(user);

        // When
        Mockito.when(userRepository.existsByEmail(mockRequest.getEmail())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // Then
        UserDTO response = customerService.createCustomer(mockRequest);

        Assertions.assertEquals(userDTO, response);
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(Mockito.anyString());
    }

    @Test
    void givenValidCreateCustomerRequest_whenEmailAlreadyExists_thenThrowEmailAlreadyExists() {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@springbookstore.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        // When
        Mockito.when(userRepository.existsByEmail(mockRequest.getEmail())).thenReturn(true);

        // Then
        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> customerService.createCustomer(mockRequest)
        );
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verify(passwordEncoder, Mockito.never()).encode(Mockito.anyString());
    }
}