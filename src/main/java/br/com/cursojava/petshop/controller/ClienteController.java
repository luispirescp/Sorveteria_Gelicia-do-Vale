package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.model.Produto;
import br.com.cursojava.petshop.service.ClienteService;
import ch.qos.logback.core.net.server.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ClienteController {
//testando nova branch developer_2_
    private final ClienteService clienteService;

    public ClienteController(ClienteService  clienteService) {
        this.clienteService = clienteService;
    }

    //Cria cliente
    @PostMapping(value = "/salva-cliente", consumes = "application/json")
    public ResponseEntity<Cliente> criaCliente(@RequestBody Cliente cliente) {
        cliente = clienteService.criaCliente(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    //Busca Todos os cliente
    @GetMapping("/todos-clientes")
    public ResponseEntity<List<Cliente>> getTodosUsuarios() {
        List<Cliente> clientes = clienteService.getClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
//
//    //Busca Cliente por nome
//    @GetMapping("/cliente/{nome}")
//    public ResponseEntity<List<Cliente>> getClientePorNome(@PathVariable String nome) {
//        return new ResponseEntity<>(clienteService.getClientePorNome(nome), HttpStatus.OK);
//    }
//
//    //Busca Cliente por cpf
//    @GetMapping("/cliente-cpf/{cpf}")
//    public ResponseEntity<List<Cliente>> getClientePorCPF(@PathVariable String cpf) {
//        return new ResponseEntity<>(clienteService.getClientePorCPF(cpf), HttpStatus.OK);
//    }
//
//    //Busca Cliente por telefone
//    @GetMapping("/cliente-telefone/{telefone}")
//    public ResponseEntity<List<Cliente>> getClientePorTelefone(@PathVariable String telefone) {
//        return new ResponseEntity<>(clienteService.getClientePorTelefone(telefone), HttpStatus.OK);
//    }
//
//    //Altera cliente
//    @PutMapping(value = "/altera-cliente", consumes = "application/json")
//    public ResponseEntity<Cliente> alteraAnimal(@RequestBody Cliente cliente) {
//        cliente = clienteService.alteraCliente(cliente);
//        return new ResponseEntity<>(cliente, HttpStatus.OK);
//    }
//    // Deleta cliente
//    @DeleteMapping(value = "/deleta-cliente", consumes = "application/json")
//    public ResponseEntity<Cliente> deletaCliente(@RequestBody Cliente cliente){
//        cliente = clienteService.deletaCliente(cliente);
//        return new ResponseEntity<>(cliente, HttpStatus.OK);
//    }
}
