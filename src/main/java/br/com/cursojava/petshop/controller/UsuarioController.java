package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Create do Crud
    @PostMapping(value = "/salva-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario) {
        usuario = usuarioService.criarUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    //Read do CRUD
    @GetMapping("/todos-usuarios")
    public ResponseEntity<List<Usuario>> getTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //Busca usuario por nome
    @GetMapping("/usuarios/{nome}")
    public ResponseEntity<List<Usuario>> getUsuariosPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(usuarioService.getUsuarioPorNome(nome), HttpStatus.OK);
    }

    //Busca usuario por email
    @GetMapping("/usuario/{email}")
    public ResponseEntity<List<Usuario>> getUsuariosPorEmail(@PathVariable String email) {
        return new ResponseEntity<>(usuarioService.getUsuarioPorEmail(email), HttpStatus.OK);
    }

    //Altera usuario
    @PutMapping (value = "/altera-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> alteraUsuario(@RequestBody Usuario usuario) {
        usuario = usuarioService.alteraUsuario(usuario);
        return new ResponseEntity<>( usuario, HttpStatus.OK);
    }
    //Deleta usuario passando usuario
    @DeleteMapping(value = "/deleta-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> deletaUsuario(@RequestBody Usuario usuario){
        usuario = usuarioService.deletaUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
