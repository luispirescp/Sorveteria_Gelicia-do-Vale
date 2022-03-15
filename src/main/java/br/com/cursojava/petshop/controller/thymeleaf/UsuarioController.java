package br.com.cursojava.petshop.controller.thymeleaf;

import br.com.cursojava.petshop.model.Usuario;
import br.com.cursojava.petshop.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("UsuarioControllerThymeleaf")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Tela de listagem de usuários
    @GetMapping("/listar-usuarios")
    public String getCadastroUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getUsuarios());
        return "listar-usuarios";
    }

    //Tela de cadastro de usuário
    @GetMapping("/adicionar-usuario")
    public String getAdicionarUsuarios(Usuario usuario, Model model, @RequestParam(required = false) Long id) {
        usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuario", usuario);
        return "adicionar-usuario";
    }

    //url de gravação dos dados do usuário
    @PostMapping("/salvar-usuario")
    public String salvarUsuario(Usuario usuario, BindingResult bindingResult) {
        usuarioService.criaOuAlteraUsuario(usuario);
        return "redirect:/listar-usuarios";
    }

    @GetMapping("/deletar-usuario/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioService.deletaUsuario(id);
        return "redirect:/listar-usuarios";
    }
}
