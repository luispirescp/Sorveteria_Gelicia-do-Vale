package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.repository.ClienteRepository;
import br.com.cursojava.petshop.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;


    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getClientes() {
        List<Cliente> clienteList = (List<Cliente>) repository.findAll();
        if (clienteList.isEmpty()) {
            return null;
        }
        return clienteList;
    }

    public void deletaCliente(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException(String.format("O cliente com id %d não existe!", id));
        }
    }

    public Cliente criarCliente(Cliente cliente) {
        if (cliente.getId() != null) {
            throw new RuntimeException("Ao criar um cliente não deve ser informado o ID!");
        }
        return repository.save(cliente);
    }

    public Cliente alteraCliente(Cliente cliente) {
        if (repository.existsById(cliente.getId())) {
            return repository.save(cliente);
        } else {
            throw new RuntimeException(String.format("O cliente com id %d não existe!", cliente.getId()));
        }
    }

    public Cliente deletaCliente(Cliente cliente) {
        if (repository.existsById(cliente.getId())) {
            repository.delete(cliente);
            return cliente;
        } else {
            throw new RuntimeException(String.format("O Cliente com id %d não existe!", cliente.getId()));
        }
    }

    public Cliente getClienteById(Long id) {
        if (id == null) {
            return new Cliente();
        }
        return repository.findById(id).orElse(null);
    }

    public void criaOuAlteraCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            criarCliente(cliente);
        } else {
            alteraCliente(cliente);
        }
    }
}
