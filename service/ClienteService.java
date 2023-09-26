package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Cliente;
import br.com.cursojava.petshop.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository ;

    public ClienteService(ClienteRepository clienteRepository ) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getClientes(){
        return (List<Cliente>) clienteRepository.findAll();
    }

    public List<Cliente> getClientePorNome(String nome){
        return clienteRepository.findByNome(nome);
    }

    public List<Cliente> getClientePorCPF(String cpf){
        return clienteRepository.findByCpf(cpf);
    }
    public List<Cliente> getClientePorTelefone(String telefone){
        return clienteRepository.findByTelefone(telefone);
    }

    public Cliente criaCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente alteraCliente(Cliente cliente){
        if(clienteRepository.existsById(cliente.getId())){
            return clienteRepository.save(cliente);
        }else {
            throw new RuntimeException(String.format("O cliente com o ID %d não existe!", cliente.getId()));
        }
    }

    public Cliente deletaCliente(Cliente cliente){
        if(clienteRepository.existsById(cliente.getId())){
            clienteRepository.delete(cliente);
            return cliente;
        }else {
            throw new RuntimeException(String.format("O cliente com o ID %d não existe!", cliente.getId()));
        }
    }
}
