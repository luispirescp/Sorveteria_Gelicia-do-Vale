package br.com.cursojava.petshop.domain.user;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    public Usuario save(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return userRepository.save(usuario);
        } catch (MappingException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao salvar usuário - Problema no mapeamento");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar usuário");
        }
    }

    public List<Usuario> getUsuarios(){
        return userRepository.findAll();
    }
}
