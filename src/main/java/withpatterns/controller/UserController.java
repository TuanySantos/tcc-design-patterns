package withpatterns.controller;

import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;
import withpatterns.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return service.findAll();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}