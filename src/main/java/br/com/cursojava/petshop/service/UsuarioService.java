package br.com.cursojava.petshop.service;

import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getUsuarios() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public List<Usuario> getUsuarioPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> getUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
