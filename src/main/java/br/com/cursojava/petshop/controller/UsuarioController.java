package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping(value = "/criar-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        usuario = usuarioService.criarUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    //Read do CRUD
    @GetMapping("/todos-usuarios")
    public ResponseEntity<List<Usuario>> getTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{nome}")
    public ResponseEntity<List<Usuario>> getUsuariosPorNome(@PathVariable String nome) {
        return new ResponseEntity<>(usuarioService.getUsuariosPorNome(nome), HttpStatus.OK);
    }

    //Alter do Crud
    @PutMapping(value = "/altera-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> alteraUsuario(@RequestBody Usuario usuario) {
        usuario = usuarioService.alteraUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    //Delete do CRUD
    @DeleteMapping(value = "/deleta-usuario", consumes = "application/json")
    public ResponseEntity<Usuario> deletaUsuario(@RequestBody Usuario usuario){
        usuario = usuarioService.deletaUsuario(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
