package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.domain.user.UserService;
import br.com.cursojava.petshop.domain.user.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

//@RestController
@CrossOrigin(origins = "*")
@RestController
public class UserController {
   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"/create-usuario"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) throws IOException {
        userService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<String> getProductById() {
        try {
            List<Usuario> usuarios = userService.getUsuarios();
            return ResponseEntity.ok().body(usuarios.toString().toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("O produto com o ID   não foi encontrado!");
        }
    }

}
