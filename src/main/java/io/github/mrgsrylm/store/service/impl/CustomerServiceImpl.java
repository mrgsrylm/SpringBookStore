package io.github.mrgsrylm.store.service.impl;

import io.github.mrgsrylm.store.dto.UserDTO;
import io.github.mrgsrylm.store.exception.user.EmailAlreadyExistsException;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.enums.Role;
import io.github.mrgsrylm.store.model.mapper.user.UserMapper;
import io.github.mrgsrylm.store.payload.request.customer.CustomerCreateRequest;
import io.github.mrgsrylm.store.repository.UserRepository;
import io.github.mrgsrylm.store.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link CustomerService} interface for creating and managing customer accounts.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    /**
     * Creates a new customer based on the provided customer creation request.
     *
     * @param request The request containing customer information to be used for creation.
     * @return A {@link User} representing the newly created customer.
     */
    @Override
    public UserDTO createCustomer(CustomerCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();

        return UserMapper.toDTO(userRepository.save(user));

    }

}
