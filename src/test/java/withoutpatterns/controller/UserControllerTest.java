package withoutpatterns.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import withoutpatterns.dto.UserRequest;
import withoutpatterns.dto.UserResponse;
import withoutpatterns.exception.ApiExceptionHandler;
import withoutpatterns.exception.ValidationException;

import withoutpatterns.service.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
@Import(ApiExceptionHandler.class)
class UserControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper mapper;

    @MockBean UserService service;

    @Test
    void getUserById_shouldReturnUser() throws Exception {
        when(service.findById(10L))
                .thenReturn(new UserResponse(10L, "A", "a@b.com", "12345678901"));

        mvc.perform(get("/users/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));

        verify(service).findById(10L);
    }

    @Test
    void putUsers_shouldReturnUpdatedUser() throws Exception {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");
        when(service.update(eq(5L), any()))
                .thenReturn(new UserResponse(5L, "Tuany", "tuany@exemplo.com", "12345678901"));

        mvc.perform(put("/users/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));

        verify(service).update(eq(5L), any());
    }

    @Test
    void deleteUsers_shouldReturnOk() throws Exception {
        doNothing().when(service).delete(7L);

        mvc.perform(delete("/users/7"))
                .andExpect(status().isOk());

        verify(service).delete(7L);
    }

    @Test
    void whenServiceThrowsValidationException_handlerShouldReturn400() throws Exception {
        var req = new UserRequest("", "x", "abc", "1");
        when(service.create(any())).thenThrow(new ValidationException("Nome é obrigatório"));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Nome é obrigatório"));
    }

    @Test
    void getUsers_shouldReturnList() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                new UserResponse(1L, "A", "a@b.com", "12345678901")
        ));

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(service).findAll();
    }

    @Test
    void postUsers_shouldReturnCreatedUser() throws Exception {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");
        when(service.create(any()))
                .thenReturn(new UserResponse(1L, "Tuany", "tuany@exemplo.com", "12345678901"));

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).create(any());
    }
}