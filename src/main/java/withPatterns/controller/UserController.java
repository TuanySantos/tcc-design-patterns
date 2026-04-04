package withPatterns.controller;

import withPatterns.model.User;
import withPatterns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Criar um novo usuário
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // Buscar um usuário pelo ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // Buscar todos os usuários
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    // Atualizar um usuário
// src/main/java/withPatterns/controller/UserController.java
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = new User(
                id,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCpf()
        );
        return userService.update(updatedUser);
    }

    // Excluir um usuário
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
