package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Animal;
import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    //Create do Crud
    @PostMapping(value = "/criar-cliente", consumes = "application/json")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        cliente = service.criarCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    //Read do CRUD
    @GetMapping("/todos-clientes")
    public ResponseEntity<List<Cliente>> getTodosClientes() {
        List<Cliente> usuarios = service.getClientes();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    //Alter do Crud
    @PutMapping(value = "/altera-cliente", consumes = "application/json")
    public ResponseEntity<Cliente> alteraCliente(@RequestBody Cliente cliente) {
        cliente = service.alteraCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
    }


    //Delete do CRUD
    @DeleteMapping(value = "/deleta-cliente", consumes = "application/json")
    public ResponseEntity<Cliente> deletaCliente(@RequestBody Cliente cliente){
        cliente = service.deletaCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.ACCEPTED);
    }

}
