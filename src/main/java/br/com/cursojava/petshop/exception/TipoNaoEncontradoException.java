package br.com.cursojava.petshop.exception;

public class TipoNaoEncontradoException extends RuntimeException {
    public TipoNaoEncontradoException(String texto) {
        super("Tipo não encontrado para: " + texto);
    }
}
