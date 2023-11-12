package io.github.mrgsrylm.store.controller;

import io.github.mrgsrylm.store.base.BaseControllerTest;
import io.github.mrgsrylm.store.builder.UserBuilder;
import io.github.mrgsrylm.store.dto.UserDTO;
import io.github.mrgsrylm.store.model.User;
import io.github.mrgsrylm.store.model.enums.Role;
import io.github.mrgsrylm.store.model.mapper.customer.CustomerMapper;
import io.github.mrgsrylm.store.model.mapper.user.UserMapper;
import io.github.mrgsrylm.store.payload.request.customer.CustomerCreateRequest;
import io.github.mrgsrylm.store.payload.response.CustomResponse;
import io.github.mrgsrylm.store.payload.response.customer.CustomerCreatedResponse;
import io.github.mrgsrylm.store.service.impl.CustomerServiceImpl;
import io.github.mrgsrylm.store.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends BaseControllerTest {
    @MockBean
    private CustomerServiceImpl customerService;

    @Test
    void givenValidCustomerCreateRequest_whenAdminRole_thenReturnCustomerCreatedResponse() throws Exception {

        // Given
        CustomerCreateRequest mockRequest = CustomerCreateRequest.builder()
                .email(RandomUtil.generateRandomString().concat("@springbookstore.com"))
                .password(RandomUtil.generateRandomString())
                .fullName(RandomUtil.generateRandomString())
                .username(RandomUtil.generateRandomString())
                .build();

        User user = new UserBuilder()
                .withId(1L)
                .withEmail(mockRequest.getEmail())
                .withFullName(mockRequest.getFullName())
                .withUsername(mockRequest.getUsername())
                .withRole(Role.ROLE_CUSTOMER)
                .build();

        UserDTO userDTO = UserMapper.toDTO(user);
        CustomerCreatedResponse customerCreatedResponse = CustomerMapper.toCreatedResponse(userDTO);

        // When
        Mockito.when(customerService.createCustomer(mockRequest)).thenReturn(userDTO);

        // Then
        CustomResponse<CustomerCreatedResponse> customResponseOfCustomerCreatedResponse = CustomResponse.created(customerCreatedResponse);

        mockMvc.perform(post("/api/v1/customers")
                        .header(HttpHeaders.AUTHORIZATION, mockAdminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.id").value(customerCreatedResponse.getId()))
                .andExpect(jsonPath("$.response.fullName").value(customerCreatedResponse.getFullName()))
                .andExpect(jsonPath("$.response.username").value(customerCreatedResponse.getUsername()))
                .andExpect(jsonPath("$.response.email").value(customerCreatedResponse.getEmail()))
                .andExpect(jsonPath("$.isSuccess").value(customResponseOfCustomerCreatedResponse.getIsSuccess()))
                .andExpect(jsonPath("$.httpStatus").value(customResponseOfCustomerCreatedResponse.getHttpStatus().name()))
                .andExpect(jsonPath("$.time").isNotEmpty());
    }
}
