package withpatterns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;
import withpatterns.exception.ApiExceptionHandler;
import withpatterns.exception.ValidationException;
import withpatterns.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(ApiExceptionHandler.class)
class UserControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean UserService service;

    @Test
    void postUsers_shouldReturnCreatedUser() throws Exception {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");
        when(service.create(any())).thenReturn(new UserResponse(1L, "Tuany", "tuany@exemplo.com", "12345678901"));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).create(any());
    }

    @Test
    void getUsers_shouldReturnList() throws Exception {
        when(service.findAll()).thenReturn(List.of(new UserResponse(1L, "A", "a@b.com", "12345678901")));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void whenValidationException_then400() throws Exception {
        var req = new UserRequest("", "x", "abc", "1");
        when(service.create(any())).thenThrow(new ValidationException("Nome é obrigatório"));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Nome é obrigatório"));
    }
}