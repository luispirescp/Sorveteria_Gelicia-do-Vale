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

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarioList = (List<Usuario>) usuarioRepository.findAll();
        if (usuarioList.isEmpty()) {
            return null;
        }
        return usuarioList;
    }

    public List<Usuario> getUsuariosPorNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario.getId() != null) {
            throw new RuntimeException("Ao criar um usuário não deve ser informado o ID!");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario alteraUsuario(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getId())) {
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException(String.format("O usuário com id %d não existe!", usuario.getId()));
        }
    }

    public Usuario deletaUsuario(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getId())) {
            usuarioRepository.delete(usuario);
            return usuario;
        } else {
            throw new RuntimeException(String.format("O usuário com id %d não existe!", usuario.getId()));
        }
    }

    public void deletaUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException(String.format("O usuário com id %d não existe!", id));
        }
    }

    public Usuario getUsuarioById(Long id) {
        if (id == null) {
            return new Usuario();
        }
        return usuarioRepository.findById(id).orElse(null);
    }

    public void criaOuAlteraUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            criarUsuario(usuario);
        } else {
            alteraUsuario(usuario);
        }
    }
}
