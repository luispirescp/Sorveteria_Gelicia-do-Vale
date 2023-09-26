package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public List<Usuario> getUsuarioPorNome(String nome){
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> getUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario criarUsuario(Usuario usuario){

        if(usuario.getId() != null){
           throw new RuntimeException("Ao criar usuário não deve ser informado ID");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario alteraUsuario(Usuario usuario){
       if(usuarioRepository.existsById(usuario.getId())){
           return usuarioRepository.save(usuario);
       }else {
           throw new RuntimeException(String.format("O usuário com o ID %d não existe!", usuario.getId()));
       }
    }

    public Usuario deletaUsuario(Usuario usuario){
        if(usuarioRepository.existsById(usuario.getId())){
             usuarioRepository.delete(usuario);
            return usuario;
        }else {
            throw new RuntimeException(String.format("O usuário com o ID %d não existe!", usuario.getId()));
        }
    }
}
