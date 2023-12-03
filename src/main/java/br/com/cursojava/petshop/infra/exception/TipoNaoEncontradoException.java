package br.com.cursojava.petshop.infra.exception;

public class TipoNaoEncontradoException extends RuntimeException {
    public TipoNaoEncontradoException(String texto) {
        super("Tipo não encontrado para: " + texto);
    }
}
