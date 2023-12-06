package br.com.cursojava.petshop.controller;

import br.com.cursojava.petshop.domain.user.DadosAutenticacao;
import br.com.cursojava.petshop.domain.user.Usuario;
import br.com.cursojava.petshop.infra.security.DadosTokenJWT;
import br.com.cursojava.petshop.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = {"/login"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity efetuarLogin(@RequestBody @Validated DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);

        var tonekJWT = tokenService.geraToken((Usuario) authentication.getPrincipal());
        System.out.println(tonekJWT);
        return ResponseEntity.ok( new DadosTokenJWT(tonekJWT));
    }


}
