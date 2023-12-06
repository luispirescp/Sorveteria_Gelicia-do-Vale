package br.com.cursojava.petshop.domain.user;

import br.com.cursojava.petshop.domain.dto.ProdutoDTO;
import br.com.cursojava.petshop.domain.produto.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(Usuario usuario) {
        Usuario usuario_ = new Usuario();
        usuario_.setLogin(usuario.getLogin());
        usuario_.setSenha(usuario.getSenha());
        userRepository.save(usuario_);
    }

    public List<Usuario> getUsuarios(){
        return userRepository.findAll();
    }
}
